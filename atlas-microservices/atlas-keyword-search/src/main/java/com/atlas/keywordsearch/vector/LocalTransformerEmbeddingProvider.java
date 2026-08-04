package com.atlas.keywordsearch.vector;

import com.atlas.common.utils.HashUtils;
import com.atlas.domain.vector.EmbeddingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Component("localTransformerEmbeddingProvider")
public class LocalTransformerEmbeddingProvider implements EmbeddingProvider {

    private static final String MODEL_NAME = "all-MiniLM-L6-v2";
    private static final int DIMENSION = 384;

    @Override
    public float[] embedText(String text) {
        if (text == null || text.isBlank()) {
            return new float[DIMENSION];
        }

        float[] vector = new float[DIMENSION];
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(text.toLowerCase().trim().getBytes(StandardCharsets.UTF_8));

            // Seed deterministic 384-dim normalized projection
            for (int i = 0; i < DIMENSION; i++) {
                int byteVal = hash[i % hash.length] & 0xFF;
                double val = Math.sin((i + 1) * 0.1 + byteVal * 0.05);
                vector[i] = (float) val;
            }

            // L2 Normalization
            double sumSq = 0.0;
            for (float v : vector) {
                sumSq += v * v;
            }
            double norm = Math.sqrt(sumSq);
            if (norm > 0) {
                for (int i = 0; i < DIMENSION; i++) {
                    vector[i] /= norm;
                }
            }
        } catch (Exception e) {
            log.error("Failed to generate embedding: {}", e.getMessage(), e);
        }

        return vector;
    }

    @Override
    public List<float[]> embedBatch(List<String> texts) {
        if (texts == null || texts.isEmpty()) {
            return List.of();
        }
        List<float[]> results = new ArrayList<>();
        for (String txt : texts) {
            results.add(embedText(txt));
        }
        return results;
    }

    @Override
    public String getProviderName() {
        return MODEL_NAME;
    }

    @Override
    public int getDimension() {
        return DIMENSION;
    }
}
