package com.atlas.memory.service;

import com.atlas.domain.memory.Memory;

public interface MemoryImportanceScorer {
    double calculateScore(Memory memory);
}
