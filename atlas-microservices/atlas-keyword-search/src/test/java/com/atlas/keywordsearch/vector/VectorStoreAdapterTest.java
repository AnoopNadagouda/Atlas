package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.DistanceMetric;
import com.atlas.domain.vector.VectorSearchResult;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class VectorStoreAdapterTest {

    @Test
    void testPgVectorAdapterStub() {
        PgVectorAdapter adapter = new PgVectorAdapter();
        adapter.storeVector("v-1", new float[]{0.1f, 0.2f}, Map.of("title", "Atlas"));
        List<VectorSearchResult> results = adapter.searchNearest(new float[]{0.1f, 0.2f}, 5, DistanceMetric.COSINE);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }

    @Test
    void testQdrantVectorAdapterStub() {
        QdrantVectorAdapter adapter = new QdrantVectorAdapter();
        adapter.storeVector("v-2", new float[]{0.5f, 0.5f}, Map.of("title", "Search"));
        List<VectorSearchResult> results = adapter.searchNearest(new float[]{0.5f, 0.5f}, 5, DistanceMetric.EUCLIDEAN);

        assertNotNull(results);
        assertTrue(results.isEmpty());
    }
}
