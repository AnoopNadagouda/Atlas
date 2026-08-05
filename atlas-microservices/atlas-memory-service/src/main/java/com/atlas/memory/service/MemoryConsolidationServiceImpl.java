package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryState;
import com.atlas.domain.memory.MemoryType;
import com.atlas.kafka.events.memory.MemoryConsolidatedEvent;
import com.atlas.memory.entity.MemoryEntity;
import com.atlas.memory.integration.KafkaMemoryEventPublisher;
import com.atlas.memory.repository.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;

@Service
public class MemoryConsolidationServiceImpl implements MemoryConsolidationService {

    private static final Logger log = LoggerFactory.getLogger(MemoryConsolidationServiceImpl.class);

    private final MemoryRepository memoryRepository;
    private final MemoryEmbeddingService embeddingService;
    private final KafkaMemoryEventPublisher eventPublisher;

    public MemoryConsolidationServiceImpl(MemoryRepository memoryRepository,
                                           MemoryEmbeddingService embeddingService,
                                           KafkaMemoryEventPublisher eventPublisher) {
        this.memoryRepository = memoryRepository;
        this.embeddingService = embeddingService;
        this.eventPublisher = eventPublisher;
    }

    @Override
    @Transactional
    public Memory consolidateMemories(String tenantId, List<String> memoryIds) {
        if (memoryIds == null || memoryIds.isEmpty()) {
            throw new IllegalArgumentException("Cannot consolidate empty memory list");
        }

        log.info("[MemoryConsolidation] Consolidating {} memories for tenant '{}'", memoryIds.size(), tenantId);
        List<MemoryEntity> entities = memoryRepository.findAllById(memoryIds);

        StringBuilder combinedContent = new StringBuilder();
        double maxImportance = 0.0;
        String agentId = null;

        for (MemoryEntity entity : entities) {
            combinedContent.append("- ").append(entity.getContent()).append("\n");
            maxImportance = Math.max(maxImportance, entity.getImportanceScore());
            if (agentId == null) agentId = entity.getAgentId();
            entity.setState(MemoryState.CONSOLIDATED);
        }

        memoryRepository.saveAll(entities);

        MemoryEntity consolidatedEntity = new MemoryEntity();
        consolidatedEntity.setId(UUID.randomUUID().toString());
        consolidatedEntity.setTenantId(tenantId);
        consolidatedEntity.setAgentId(agentId);
        consolidatedEntity.setKey("consolidated-" + System.currentTimeMillis());
        consolidatedEntity.setContent("Consolidated Memory Summary:\n" + combinedContent);
        consolidatedEntity.setType(MemoryType.LONG_TERM);
        consolidatedEntity.setState(MemoryState.ACTIVE);
        consolidatedEntity.setImportanceScore(Math.min(1.0, maxImportance * 1.2));
        consolidatedEntity.setDecayFactor(1.0);
        consolidatedEntity.setAccessCount(1);
        consolidatedEntity.setCreatedAt(Instant.now());
        consolidatedEntity.setUpdatedAt(Instant.now());
        consolidatedEntity.setLastAccessedAt(Instant.now());

        MemoryEntity saved = memoryRepository.save(consolidatedEntity);

        eventPublisher.publishConsolidated(new MemoryConsolidatedEvent(
            UUID.randomUUID().toString(),
            saved.getId(),
            tenantId,
            memoryIds,
            Instant.now()
        ));

        return mapToDomain(saved);
    }

    @Override
    @Transactional
    public int runAutoConsolidation(String tenantId) {
        log.info("[MemoryConsolidation] Running automatic background consolidation for tenant '{}'", tenantId);
        List<MemoryEntity> shortTermMemories = memoryRepository.findByTenantIdAndType(tenantId, MemoryType.SHORT_TERM);

        if (shortTermMemories.size() < 3) {
            log.debug("[MemoryConsolidation] Not enough short-term memories ({}) for auto-consolidation", shortTermMemories.size());
            return 0;
        }

        List<String> idsToConsolidate = shortTermMemories.stream().limit(5).map(MemoryEntity::getId).toList();
        consolidateMemories(tenantId, idsToConsolidate);
        return idsToConsolidate.size();
    }

    private Memory mapToDomain(MemoryEntity entity) {
        Memory memory = new Memory();
        memory.setId(entity.getId());
        memory.setTenantId(entity.getTenantId());
        memory.setAgentId(entity.getAgentId());
        memory.setConversationId(entity.getConversationId());
        memory.setWorkflowId(entity.getWorkflowId());
        memory.setKey(entity.getKey());
        memory.setContent(entity.getContent());
        memory.setType(entity.getType());
        memory.setState(entity.getState());
        memory.setImportanceScore(entity.getImportanceScore());
        memory.setDecayFactor(entity.getDecayFactor());
        memory.setAccessCount(entity.getAccessCount());
        memory.setCreatedAt(entity.getCreatedAt());
        memory.setUpdatedAt(entity.getUpdatedAt());
        memory.setLastAccessedAt(entity.getLastAccessedAt());
        memory.setExpiresAt(entity.getExpiresAt());
        return memory;
    }
}
