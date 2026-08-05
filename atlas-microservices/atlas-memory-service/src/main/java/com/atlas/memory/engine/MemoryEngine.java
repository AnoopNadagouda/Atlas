package com.atlas.memory.engine;

import com.atlas.common.dto.memory.MemoryCreateRequest;
import com.atlas.common.dto.memory.MemorySearchRequest;
import com.atlas.common.dto.memory.MemoryUpdateRequest;
import com.atlas.domain.memory.Memory;
import com.atlas.domain.memory.MemoryAnalytics;
import com.atlas.domain.memory.MemoryRelation;
import com.atlas.domain.memory.MemorySearchResult;

import java.util.List;

public interface MemoryEngine {
    Memory createMemory(String tenantId, MemoryCreateRequest request);
    Memory updateMemory(String tenantId, String memoryId, MemoryUpdateRequest request);
    void deleteMemory(String tenantId, String memoryId);
    Memory getMemory(String tenantId, String memoryId);
    List<Memory> listMemories(String tenantId);
    List<MemorySearchResult> searchMemories(String tenantId, MemorySearchRequest request);
    Memory consolidateMemories(String tenantId, List<String> memoryIds);
    MemoryRelation linkMemories(String tenantId, String sourceId, String targetId, String relationType, double weight);
    List<MemoryRelation> getRelations(String memoryId);
    MemoryAnalytics getAnalytics(String tenantId);
    String exportMemories(String tenantId);
    int importMemories(String tenantId, String jsonData);
}
