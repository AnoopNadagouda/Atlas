package com.atlas.keywordsearch.analytics;

import com.atlas.domain.analytics.QualityMetrics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RelevanceEvaluatorTest {

    private RelevanceEvaluator evaluator;

    @BeforeEach
    void setUp() {
        evaluator = new RelevanceEvaluator();
    }

    @Test
    void testComputeQualityMetrics() {
        QualityMetrics metrics = evaluator.computeMetrics();
        assertNotNull(metrics);
        assertTrue(metrics.getNdcgAt10() > 0.0);
        assertTrue(metrics.getMrr() > 0.0);
        assertTrue(metrics.getCtr() > 0.0);
    }
}
