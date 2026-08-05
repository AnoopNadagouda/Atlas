package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;

import java.util.List;

public interface MemoryConsolidationService {
    Memory consolidateMemories(String tenantId, List<String> memoryIds);
    int runAutoConsolidation(String tenantId);
}
