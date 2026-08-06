package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryRelation;
import java.util.List;

public interface MemoryRelationshipService {
    MemoryRelation createRelationship(String tenantId, String sourceId, String targetId, String relationType, double weight);
    List<MemoryRelation> getRelationships(String memoryId);
    void deleteRelationship(String relationshipId);
}
