package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BackgroundMergeEngineTest {

    private BackgroundMergeEngine mergeEngine;
    private SegmentRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new SegmentRegistry();
        registry.initSeedSegments();
        mergeEngine = new BackgroundMergeEngine(registry);
    }

    @Test
    void testMergeSegments() {
        SegmentMeta merged = mergeEngine.mergeSegments("shard-0");
        assertNotNull(merged);
        assertTrue(merged.getSegmentId().startsWith("seg-merged-"));
        assertEquals(500000, merged.getDocumentCount());
        assertEquals(1, registry.getActiveSegments("shard-0").size());
    }
}
