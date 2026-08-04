package com.atlas.keywordsearch.vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LocalTransformerEmbeddingProviderTest {

    private LocalTransformerEmbeddingProvider provider;

    @BeforeEach
    void setUp() {
        provider = new LocalTransformerEmbeddingProvider();
    }

    @Test
    void testEmbeddingGeneration() {
        float[] vector = provider.embedText("Atlas cloud search engine");

        assertNotNull(vector);
        assertEquals(384, vector.length);

        // Verify L2 normalization: sum of squares should equal ~1.0
        double sumSq = 0.0;
        for (float v : vector) {
            sumSq += v * v;
        }
        assertEquals(1.0, sumSq, 0.01);
    }
}
