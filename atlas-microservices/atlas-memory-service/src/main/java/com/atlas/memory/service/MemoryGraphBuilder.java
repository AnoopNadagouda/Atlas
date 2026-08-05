package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryRelation;

import java.util.List;

public interface MemoryGraphBuilder {
    MemoryRelation linkMemories(String sourceId, String targetId, String relationType, double weight);
    List<MemoryRelation> getRelationsForMemory(String memoryId);
}
