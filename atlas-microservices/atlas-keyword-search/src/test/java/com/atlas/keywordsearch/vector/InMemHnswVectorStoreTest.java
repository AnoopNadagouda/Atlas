package com.atlas.keywordsearch.vector;

import com.atlas.domain.vector.DistanceMetric;
import com.atlas.domain.vector.VectorSearchResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InMemHnswVectorStoreTest {

    private InMemHnswVectorStore vectorStore;

    @BeforeEach
    void setUp() {
        vectorStore = new InMemHnswVectorStore();
    }

    @Test
    void testVectorStorageAndNearestSearch() {
        float[] v1 = new float[384];
        v1[0] = 1.0f;

        float[] v2 = new float[384];
        v2[0] = 0.9f;
        v2[1] = 0.1f;

        vectorStore.storeVector("doc-1", v1, Map.of("title", "Atlas"));
        vectorStore.storeVector("doc-2", v2, Map.of("title", "Search"));

        assertEquals(2, vectorStore.getVectorCount());

        List<VectorSearchResult> results = vectorStore.searchNearest(v1, 5, DistanceMetric.COSINE);

        assertFalse(results.isEmpty());
        assertEquals("doc-1", results.get(0).getId());
        assertTrue(results.get(0).getSimilarityScore() > 0.99);
    }
}
