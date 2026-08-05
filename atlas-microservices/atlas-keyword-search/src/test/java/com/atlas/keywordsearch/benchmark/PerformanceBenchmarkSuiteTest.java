package com.atlas.keywordsearch.benchmark;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PerformanceBenchmarkSuiteTest {

    private PerformanceBenchmarkSuite benchmarkSuite;

    @BeforeEach
    void setUp() {
        benchmarkSuite = new PerformanceBenchmarkSuite();
    }

    @Test
    void testRunBenchmarks() {
        Map<String, Object> report = benchmarkSuite.runBenchmarks();
        assertNotNull(report);
        assertEquals("PASSED_ALL_BENCHMARKS", report.get("status"));
        assertTrue((int) report.get("queriesPerSecondQps") > 5000);
    }
}
