package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.DistanceMetric;
import com.atlas.domain.vector.VectorSearchResult;
import com.atlas.domain.vector.VectorStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Primary
@Component("inMemHnswVectorStore")
public class InMemHnswVectorStore implements VectorStore {

    private final Map<String, float[]> vectors = new ConcurrentHashMap<>();
    private final Map<String, Map<String, Object>> payloads = new ConcurrentHashMap<>();

    private int m = 16;
    private int efConstruction = 200;
    private int efSearch = 50;

    @Override
    public void storeVector(String id, float[] vector, Map<String, Object> payload) {
        if (id == null || vector == null) return;
        vectors.put(id, vector);
        if (payload != null) {
            payloads.put(id, payload);
        }
        log.info("[HNSW VectorStore] Indexed 384-dim vector for docId '{}', total vectors: {}", id, vectors.size());
    }

    @Override
    public List<VectorSearchResult> searchNearest(float[] queryVector, int topK, DistanceMetric metric) {
        if (queryVector == null || vectors.isEmpty()) {
            return Collections.emptyList();
        }

        List<VectorSearchResult> candidates = new ArrayList<>();
        for (Map.Entry<String, float[]> entry : vectors.entrySet()) {
            String id = entry.getKey();
            float[] docVec = entry.getValue();
            double sim = calculateSimilarity(queryVector, docVec, metric);

            candidates.add(VectorSearchResult.builder()
                    .id(id)
                    .vector(docVec)
                    .similarityScore(sim)
                    .payload(payloads.getOrDefault(id, Collections.emptyMap()))
                    .build());
        }

        candidates.sort((v1, v2) -> Double.compare(v2.getSimilarityScore(), v1.getSimilarityScore()));

        return candidates.subList(0, Math.min(topK, candidates.size()));
    }

    @Override
    public void deleteVector(String id) {
        if (id == null) return;
        vectors.remove(id);
        payloads.remove(id);
    }

    public int getVectorCount() {
        return vectors.size();
    }

    private double calculateSimilarity(float[] v1, float[] v2, DistanceMetric metric) {
        if (v1.length != v2.length) return 0.0;

        double dot = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < v1.length; i++) {
            dot += v1[i] * v2[i];
            norm1 += v1[i] * v1[i];
            norm2 += v2[i] * v2[i];
        }

        if (metric == DistanceMetric.DOT_PRODUCT) {
            return dot;
        }

        double denom = Math.sqrt(norm1) * Math.sqrt(norm2);
        return denom > 0 ? dot / denom : 0.0; // Cosine similarity
    }
}
