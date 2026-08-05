package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryEmbedding;

public interface MemoryEmbeddingService {
    MemoryEmbedding generateEmbedding(String memoryId, String text);
    double calculateSimilarity(float[] vec1, float[] vec2);
}
