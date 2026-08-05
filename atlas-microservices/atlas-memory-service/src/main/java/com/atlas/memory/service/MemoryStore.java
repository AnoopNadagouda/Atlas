package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;

import java.util.List;
import java.util.Optional;

public interface MemoryStore {
    Memory saveMemory(Memory memory);
    Optional<Memory> findMemoryById(String id);
    List<Memory> findMemoriesByTenant(String tenantId);
    void deleteMemory(String id);
}
