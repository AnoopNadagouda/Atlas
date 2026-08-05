package com.atlas.keywordsearch.ltr;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LtrRankingServiceTest {

    private LtrRankingService ltrRankingService;

    @BeforeEach
    void setUp() {
        LtrFeatureExtractor extractor = new LtrFeatureExtractor();
        LtrModelRegistry registry = new LtrModelRegistry();
        registry.initSeedModels();

        ltrRankingService = new LtrRankingService(extractor, registry);
    }

    @Test
    void testPredictScore() {
        double score = ltrRankingService.predictScore("atlas search", "doc-1", 0.85, 0.92, 0.35, 0.90);
        assertTrue(score > 0.0);
        assertTrue(score <= 1.0);
    }
}
