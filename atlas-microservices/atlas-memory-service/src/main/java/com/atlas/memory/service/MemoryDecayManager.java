package com.atlas.memory.service;

import com.atlas.memory.entity.MemoryEntity;

import java.util.List;

public interface MemoryDecayManager {
    void applyDecay(List<MemoryEntity> memories);
    double computeDecayFactor(MemoryEntity entity);
}
