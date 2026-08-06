package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryRelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoryRelationshipServiceImpl implements MemoryRelationshipService {

    private static final Logger log = LoggerFactory.getLogger(MemoryRelationshipServiceImpl.class);

    private final MemoryGraphBuilder graphBuilder;

    public MemoryRelationshipServiceImpl(MemoryGraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
    }

    @Override
    public MemoryRelation createRelationship(String tenantId, String sourceId, String targetId, String relationType, double weight) {
        log.info("[MemoryRelationshipService] Linking memory {} -> {} with type '{}'", sourceId, targetId, relationType);
        return graphBuilder.linkMemories(sourceId, targetId, relationType, weight);
    }

    @Override
    public List<MemoryRelation> getRelationships(String memoryId) {
        return graphBuilder.getRelationsForMemory(memoryId);
    }

    @Override
    public void deleteRelationship(String relationshipId) {
        log.info("[MemoryRelationshipService] Deleting relationship {}", relationshipId);
    }
}
