package com.atlas.memory.service;

import com.atlas.common.dto.memory.MemorySearchRequest;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemorySearchResult;
import com.atlas.memory.entity.MemoryEntity;
import com.atlas.memory.repository.MemoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class MemorySearchServiceImpl implements MemorySearchService {

    private static final Logger log = LoggerFactory.getLogger(MemorySearchServiceImpl.class);

    private final MemoryRepository memoryRepository;
    private final MemoryImportanceScorer importanceScorer;

    public MemorySearchServiceImpl(MemoryRepository memoryRepository,
                                    MemoryImportanceScorer importanceScorer) {
        this.memoryRepository = memoryRepository;
        this.importanceScorer = importanceScorer;
    }

    @Override
    public List<MemorySearchResult> searchMemories(String tenantId, MemorySearchRequest request) {
        log.info("[MemorySearch] Searching memories for query: '{}', tenant: '{}'", request.getQuery(), tenantId);

        List<MemoryEntity> entities;
        if (request.getAgentId() != null && !request.getAgentId().isBlank()) {
            entities = memoryRepository.findByTenantIdAndAgentId(tenantId, request.getAgentId());
        } else if (request.getType() != null) {
            entities = memoryRepository.findByTenantIdAndType(tenantId, request.getType());
        } else {
            entities = memoryRepository.findByTenantId(tenantId);
        }

        List<MemorySearchResult> results = new ArrayList<>();
        String queryLower = request.getQuery() != null ? request.getQuery().toLowerCase() : "";

        for (MemoryEntity entity : entities) {
            Memory memory = mapToDomain(entity);
            double importance = importanceScorer.calculateScore(memory);

            if (importance < request.getMinImportance()) {
                continue;
            }

            double relevance = 0.5;
            if (!queryLower.isBlank()) {
                if (entity.getContent().toLowerCase().contains(queryLower) ||
                    entity.getKey().toLowerCase().contains(queryLower)) {
                    relevance = 0.9;
                } else {
                    relevance = 0.2;
                }
            }

            double finalScore = (relevance * 0.6) + (importance * 0.4);
            results.add(new MemorySearchResult(memory, finalScore, relevance));
        }

        results.sort(Comparator.comparingDouble(MemorySearchResult::getRelevanceScore).reversed());
        int limit = request.getLimit() > 0 ? request.getLimit() : 10;
        return results.stream().limit(limit).toList();
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
