package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.DistanceMetric;
import com.atlas.domain.vector.VectorSearchResult;
import com.atlas.domain.vector.VectorStore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("qdrantVectorStore")
public class QdrantVectorAdapter implements VectorStore {

    @Override
    public void storeVector(String id, float[] vector, Map<String, Object> payload) {
        log.info("[QdrantVectorAdapter Stub] Storing vector for id: '{}'", id);
    }

    @Override
    public List<VectorSearchResult> searchNearest(float[] queryVector, int topK, DistanceMetric metric) {
        log.info("[QdrantVectorAdapter Stub] Searching top {} nearest vectors via metric: {}", topK, metric);
        return Collections.emptyList();
    }

    @Override
    public void deleteVector(String id) {
        log.info("[QdrantVectorAdapter Stub] Deleting vector for id: '{}'", id);
    }
}
