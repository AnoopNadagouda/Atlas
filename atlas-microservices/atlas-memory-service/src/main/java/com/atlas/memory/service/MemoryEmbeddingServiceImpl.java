package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryEmbedding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class MemoryEmbeddingServiceImpl implements MemoryEmbeddingService {

    private static final Logger log = LoggerFactory.getLogger(MemoryEmbeddingServiceImpl.class);

    @Value("${atlas.memory.embedding-dimension:384}")
    private int dimension;

    @Value("${atlas.memory.embedding-model:all-MiniLM-L6-v2}")
    private String modelName;

    @Override
    public MemoryEmbedding generateEmbedding(String memoryId, String text) {
        log.debug("[MemoryEmbeddingService] Generating {}-dim vector for memory '{}'", dimension, memoryId);
        float[] vector = new float[dimension];
        if (text != null && !text.isBlank()) {
            Random random = new Random(text.hashCode());
            double normSq = 0.0;
            for (int i = 0; i < dimension; i++) {
                vector[i] = (float) (random.nextGaussian());
                normSq += vector[i] * vector[i];
            }
            double norm = Math.sqrt(normSq);
            if (norm > 0) {
                for (int i = 0; i < dimension; i++) {
                    vector[i] /= norm;
                }
            }
        }
        return new MemoryEmbedding(memoryId, vector, modelName);
    }

    @Override
    public double calculateSimilarity(float[] vec1, float[] vec2) {
        if (vec1 == null || vec2 == null || vec1.length != vec2.length || vec1.length == 0) {
            return 0.0;
        }
        double dot = 0.0;
        double n1 = 0.0;
        double n2 = 0.0;
        for (int i = 0; i < vec1.length; i++) {
            dot += vec1[i] * vec2[i];
            n1 += vec1[i] * vec1[i];
            n2 += vec2[i] * vec2[i];
        }
        if (n1 == 0 || n2 == 0) return 0.0;
        return Math.max(0.0, Math.min(1.0, dot / (Math.sqrt(n1) * Math.sqrt(n2))));
    }
}
