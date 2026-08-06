package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryEmbedding;
import com.atlas.memory.entity.MemoryEmbeddingEntity;
import com.atlas.memory.repository.MemoryEmbeddingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class MemoryIndexerImpl implements MemoryIndexer {

    private static final Logger log = LoggerFactory.getLogger(MemoryIndexerImpl.class);

    private final MemoryEmbeddingService embeddingService;
    private final MemoryEmbeddingRepository embeddingRepository;
    private final ObjectMapper objectMapper;

    public MemoryIndexerImpl(MemoryEmbeddingService embeddingService,
                             MemoryEmbeddingRepository embeddingRepository,
                             ObjectMapper objectMapper) {
        this.embeddingService = embeddingService;
        this.embeddingRepository = embeddingRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void indexMemory(Memory memory) {
        if (memory == null || memory.getContent() == null) return;
        log.info("[MemoryIndexer] Indexing vector embedding for memoryId: {}", memory.getId());
        try {
            MemoryEmbedding embedding = embeddingService.generateEmbedding(memory.getId(), memory.getContent());
            MemoryEmbeddingEntity entity = embeddingRepository.findById(memory.getId())
                    .orElseGet(() -> {
                        MemoryEmbeddingEntity newEntity = new MemoryEmbeddingEntity();
                        newEntity.setMemoryId(memory.getId());
                        return newEntity;
                    });

            entity.setVectorJson(objectMapper.writeValueAsString(embedding.getVector()));
            entity.setModelName(embedding.getModelName());
            entity.setDimension(embedding.getDimension());
            entity.setCreatedAt(Instant.now());
            embeddingRepository.save(entity);
        } catch (Exception e) {
            log.warn("[MemoryIndexer] Failed to index memory vector for '{}': {}", memory.getId(), e.getMessage());
        }
    }

    @Override
    public void removeFromIndex(String memoryId) {
        log.info("[MemoryIndexer] Removing memoryId from index: {}", memoryId);
        embeddingRepository.deleteById(memoryId);
    }

    @Override
    public void reindexAll(String tenantId) {
        log.info("[MemoryIndexer] Reindexing all memories for tenantId: {}", tenantId);
    }
}
