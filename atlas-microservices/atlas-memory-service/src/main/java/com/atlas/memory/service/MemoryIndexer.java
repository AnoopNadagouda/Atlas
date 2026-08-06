package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;

public interface MemoryIndexer {
    void indexMemory(Memory memory);
    void removeFromIndex(String memoryId);
    void reindexAll(String tenantId);
}
