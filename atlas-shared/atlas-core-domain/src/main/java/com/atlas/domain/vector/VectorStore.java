package com.atlas.domain.vector;

import java.util.List;
import java.util.Map;

public interface VectorStore {
    void storeVector(String id, float[] vector, Map<String, Object> payload);
    List<VectorSearchResult> searchNearest(float[] queryVector, int topK, DistanceMetric metric);
    void deleteVector(String id);
}
