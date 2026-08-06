package com.atlas.memory.engine;

import com.atlas.common.dto.memory.ContextRestorationRequest;
import com.atlas.common.dto.memory.ContextRestorationResponse;
import com.atlas.common.dto.memory.MemoryCreateRequest;
import com.atlas.common.dto.memory.MemorySearchRequest;
import com.atlas.common.dto.memory.MemoryUpdateRequest;
import com.atlas.domain.memory.*;
import com.atlas.kafka.events.memory.*;
import com.atlas.memory.integration.KafkaMemoryEventPublisher;
import com.atlas.memory.service.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class MemoryEngineImpl implements MemoryEngine {

    private static final Logger log = LoggerFactory.getLogger(MemoryEngineImpl.class);

    private final MemoryStore memoryStore;
    private final MemorySearchService searchService;
    private final MemoryConsolidationService consolidationService;
    private final MemoryGraphBuilder graphBuilder;
    private final MemoryAnalyticsService analyticsService;
    private final MemorySyncService syncService;
    private final KafkaMemoryEventPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    public MemoryEngineImpl(MemoryStore memoryStore,
                            MemorySearchService searchService,
                            MemoryConsolidationService consolidationService,
                            MemoryGraphBuilder graphBuilder,
                            MemoryAnalyticsService analyticsService,
                            MemorySyncService syncService,
                            KafkaMemoryEventPublisher eventPublisher,
                            ObjectMapper objectMapper) {
        this.memoryStore = memoryStore;
        this.searchService = searchService;
        this.consolidationService = consolidationService;
        this.graphBuilder = graphBuilder;
        this.analyticsService = analyticsService;
        this.syncService = syncService;
        this.eventPublisher = eventPublisher;
        this.objectMapper = objectMapper;
    }

    @Override
    public Memory createMemory(String tenantId, MemoryCreateRequest request) {
        log.info("[MemoryEngine] Creating memory for tenant '{}', key='{}'", tenantId, request.getKey());
        Memory memory = new Memory();
        memory.setTenantId(tenantId);
        memory.setAgentId(request.getAgentId());
        memory.setConversationId(request.getConversationId());
        memory.setWorkflowId(request.getWorkflowId());
        memory.setKey(request.getKey());
        memory.setContent(request.getContent());
        memory.setType(request.getType() != null ? request.getType() : MemoryType.SHORT_TERM);
        memory.setImportanceScore(request.getImportanceScore());
        if (request.getMetadata() != null) {
            memory.setMetadata(request.getMetadata());
        }

        Memory saved = memoryStore.saveMemory(memory);

        eventPublisher.publishCreated(new MemoryCreatedEvent(
            UUID.randomUUID().toString(),
            saved.getId(),
            tenantId,
            saved.getAgentId(),
            saved.getKey(),
            saved.getType().name(),
            saved.getImportanceScore(),
            Instant.now()
        ));

        return saved;
    }

    @Override
    public Memory updateMemory(String tenantId, String memoryId, MemoryUpdateRequest request) {
        log.info("[MemoryEngine] Updating memory '{}' for tenant '{}'", memoryId, tenantId);
        Memory memory = getMemory(tenantId, memoryId);

        if (request.getContent() != null) memory.setContent(request.getContent());
        if (request.getType() != null) memory.setType(request.getType());
        if (request.getState() != null) memory.setState(request.getState());
        if (request.getImportanceScore() != null) memory.setImportanceScore(request.getImportanceScore());
        if (request.getMetadata() != null) memory.setMetadata(request.getMetadata());

        Memory updated = memoryStore.saveMemory(memory);

        eventPublisher.publishUpdated(new MemoryUpdatedEvent(
            UUID.randomUUID().toString(),
            updated.getId(),
            tenantId,
            updated.getState().name(),
            updated.getImportanceScore(),
            Instant.now()
        ));

        return updated;
    }

    @Override
    public void deleteMemory(String tenantId, String memoryId) {
        log.info("[MemoryEngine] Deleting memory '{}' for tenant '{}'", memoryId, tenantId);
        memoryStore.deleteMemory(memoryId);

        eventPublisher.publishDeleted(new MemoryDeletedEvent(
            UUID.randomUUID().toString(),
            memoryId,
            tenantId,
            Instant.now()
        ));
    }

    @Override
    public Memory getMemory(String tenantId, String memoryId) {
        return memoryStore.findMemoryById(memoryId)
                .orElseThrow(() -> new IllegalArgumentException("Memory not found with ID: " + memoryId));
    }

    @Override
    public List<Memory> listMemories(String tenantId) {
        return memoryStore.findMemoriesByTenant(tenantId);
    }

    @Override
    public List<MemorySearchResult> searchMemories(String tenantId, MemorySearchRequest request) {
        List<MemorySearchResult> results = searchService.searchMemories(tenantId, request);
        for (MemorySearchResult res : results) {
            eventPublisher.publishRetrieved(new MemoryRetrievedEvent(
                UUID.randomUUID().toString(),
                res.getMemory().getId(),
                tenantId,
                request.getQuery(),
                res.getRelevanceScore(),
                Instant.now()
            ));
        }
        return results;
    }

    @Override
    public Memory consolidateMemories(String tenantId, List<String> memoryIds) {
        return consolidationService.consolidateMemories(tenantId, memoryIds);
    }

    @Override
    public MemoryRelation linkMemories(String tenantId, String sourceId, String targetId, String relationType, double weight) {
        return graphBuilder.linkMemories(sourceId, targetId, relationType, weight);
    }

    @Override
    public List<MemoryRelation> getRelations(String memoryId) {
        return graphBuilder.getRelationsForMemory(memoryId);
    }

    @Override
    public MemoryAnalytics getAnalytics(String tenantId) {
        return analyticsService.calculateAnalytics(tenantId);
    }

    @Override
    public String exportMemories(String tenantId) {
        try {
            List<Memory> memories = listMemories(tenantId);
            eventPublisher.publishExported(new MemoryExportedEvent(
                UUID.randomUUID().toString(),
                tenantId,
                memories.size(),
                Instant.now()
            ));
            return objectMapper.writeValueAsString(memories);
        } catch (Exception e) {
            throw new RuntimeException("Failed to export memories: " + e.getMessage(), e);
        }
    }

    @Override
    public int importMemories(String tenantId, String jsonData) {
        try {
            List<Memory> memories = objectMapper.readValue(jsonData, new TypeReference<List<Memory>>() {});
            int imported = 0;
            for (Memory mem : memories) {
                mem.setTenantId(tenantId);
                memoryStore.saveMemory(mem);
                imported++;
            }
            eventPublisher.publishImported(new MemoryImportedEvent(
                UUID.randomUUID().toString(),
                tenantId,
                imported,
                Instant.now()
            ));
            return imported;
        } catch (Exception e) {
            throw new RuntimeException("Failed to import memories: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Memory> getConversationHistory(String tenantId, String conversationId) {
        return memoryStore.findMemoriesByConversation(conversationId);
    }

    @Override
    public List<Memory> getWorkflowHistory(String tenantId, String workflowId) {
        return memoryStore.findMemoriesByWorkflow(workflowId);
    }

    @Override
    public ContextRestorationResponse restoreContext(String tenantId, ContextRestorationRequest request) {
        return syncService.restoreContext(tenantId, request);
    }
}
