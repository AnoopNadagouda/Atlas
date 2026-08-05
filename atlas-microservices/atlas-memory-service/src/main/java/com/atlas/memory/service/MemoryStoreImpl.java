package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryEmbedding;
import com.atlas.memory.entity.MemoryEmbeddingEntity;
import com.atlas.memory.entity.MemoryEntity;
import com.atlas.memory.repository.MemoryEmbeddingRepository;
import com.atlas.memory.repository.MemoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class MemoryStoreImpl implements MemoryStore {

    private static final Logger log = LoggerFactory.getLogger(MemoryStoreImpl.class);

    private final MemoryRepository memoryRepository;
    private final MemoryEmbeddingRepository embeddingRepository;
    private final MemoryEmbeddingService embeddingService;
    private final ObjectMapper objectMapper;

    public MemoryStoreImpl(MemoryRepository memoryRepository,
                           MemoryEmbeddingRepository embeddingRepository,
                           MemoryEmbeddingService embeddingService,
                           ObjectMapper objectMapper) {
        this.memoryRepository = memoryRepository;
        this.embeddingRepository = embeddingRepository;
        this.embeddingService = embeddingService;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public Memory saveMemory(Memory memory) {
        if (memory.getId() == null || memory.getId().isBlank()) {
            memory.setId(UUID.randomUUID().toString());
        }

        log.info("[MemoryStore] Persisting memory id='{}', key='{}', type='{}'", memory.getId(), memory.getKey(), memory.getType());
        MemoryEntity entity = mapToEntity(memory);
        MemoryEntity saved = memoryRepository.save(entity);

        try {
            MemoryEmbedding embedding = embeddingService.generateEmbedding(saved.getId(), saved.getContent());
            MemoryEmbeddingEntity embeddingEntity = new MemoryEmbeddingEntity();
            embeddingEntity.setMemoryId(saved.getId());
            embeddingEntity.setVectorJson(objectMapper.writeValueAsString(embedding.getVector()));
            embeddingEntity.setModelName(embedding.getModelName());
            embeddingEntity.setDimension(embedding.getDimension());
            embeddingEntity.setCreatedAt(Instant.now());
            embeddingRepository.save(embeddingEntity);
        } catch (Exception e) {
            log.warn("[MemoryStore] Failed to store vector embedding for memory '{}': {}", saved.getId(), e.getMessage());
        }

        return mapToDomain(saved);
    }

    @Override
    public Optional<Memory> findMemoryById(String id) {
        return memoryRepository.findById(id).map(entity -> {
            entity.setAccessCount(entity.getAccessCount() + 1);
            entity.setLastAccessedAt(Instant.now());
            memoryRepository.save(entity);
            return mapToDomain(entity);
        });
    }

    @Override
    public List<Memory> findMemoriesByTenant(String tenantId) {
        return memoryRepository.findByTenantId(tenantId).stream().map(this::mapToDomain).toList();
    }

    @Override
    @Transactional
    public void deleteMemory(String id) {
        log.info("[MemoryStore] Deleting memory id='{}'", id);
        embeddingRepository.deleteById(id);
        memoryRepository.deleteById(id);
    }

    private MemoryEntity mapToEntity(Memory memory) {
        MemoryEntity entity = new MemoryEntity();
        entity.setId(memory.getId());
        entity.setTenantId(memory.getTenantId() != null ? memory.getTenantId() : "default-tenant");
        entity.setAgentId(memory.getAgentId());
        entity.setConversationId(memory.getConversationId());
        entity.setWorkflowId(memory.getWorkflowId());
        entity.setKey(memory.getKey());
        entity.setContent(memory.getContent());
        entity.setType(memory.getType());
        entity.setState(memory.getState());
        entity.setImportanceScore(memory.getImportanceScore());
        entity.setDecayFactor(memory.getDecayFactor());
        entity.setAccessCount(memory.getAccessCount());
        entity.setCreatedAt(memory.getCreatedAt() != null ? memory.getCreatedAt() : Instant.now());
        entity.setUpdatedAt(Instant.now());
        entity.setLastAccessedAt(memory.getLastAccessedAt() != null ? memory.getLastAccessedAt() : Instant.now());
        entity.setExpiresAt(memory.getExpiresAt());

        try {
            if (memory.getMetadata() != null && !memory.getMetadata().isEmpty()) {
                entity.setMetadataJson(objectMapper.writeValueAsString(memory.getMetadata()));
            }
        } catch (Exception e) {
            log.warn("Failed to serialize metadata: {}", e.getMessage());
        }
        return entity;
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

        try {
            if (entity.getMetadataJson() != null && !entity.getMetadataJson().isBlank()) {
                memory.setMetadata(objectMapper.readValue(entity.getMetadataJson(), Map.class));
            }
        } catch (Exception e) {
            log.warn("Failed to deserialize metadata: {}", e.getMessage());
        }
        return memory;
    }
}
