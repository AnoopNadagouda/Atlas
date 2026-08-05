package com.atlas.keywordsearch.analytics;

import com.atlas.domain.analytics.RankingExperiment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExperimentManagerTest {

    private ExperimentManager experimentManager;

    @BeforeEach
    void setUp() {
        experimentManager = new ExperimentManager();
        experimentManager.initSeedExperiments();
    }

    @Test
    void testExperimentLifecycle() {
        RankingExperiment exp = experimentManager.startExperiment("exp-test", "Test Experiment", 50, "BM25_HEAVY");
        assertNotNull(exp);
        assertEquals("RUNNING", exp.getStatus());

        experimentManager.stopExperiment("exp-test");
        List<RankingExperiment> list = experimentManager.getActiveExperiments();
        assertTrue(list.stream().anyMatch(e -> e.getExperimentId().equals("exp-test") && e.getStatus().equals("STOPPED")));
    }
}
