package com.atlas.keywordsearch.history;

import com.atlas.domain.history.DocumentVersion;
import com.atlas.domain.history.VersionDiff;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class DifferenceEngineTest {

    private DifferenceEngine differenceEngine;

    @BeforeEach
    void setUp() {
        differenceEngine = new DifferenceEngine();
    }

    @Test
    void testComputeDiff() {
        DocumentVersion v1 = DocumentVersion.builder().docId("d1").versionId("v1").title("T1").snippet("S1").build();
        DocumentVersion v2 = DocumentVersion.builder().docId("d1").versionId("v2").title("T2").snippet("S2").build();

        VersionDiff diff = differenceEngine.computeDiff(v1, v2);
        assertNotNull(diff);
        assertEquals("d1", diff.getDocId());
        assertEquals(0.82, diff.getSimilarityScore());
    }
}
