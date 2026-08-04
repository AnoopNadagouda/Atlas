package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import com.atlas.domain.index.SegmentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IncrementalIndexWriterTest {

    private IncrementalIndexWriter writer;
    private SegmentRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new SegmentRegistry();
        writer = new IncrementalIndexWriter(registry);
    }

    @Test
    void testWriteIncrementalSegment() {
        SegmentMeta meta = writer.writeIncrementalSegment("shard-0", 1000, 204800);
        assertNotNull(meta);
        assertTrue(meta.getSegmentId().startsWith("seg-inc-"));
        assertEquals(SegmentState.ACTIVE, meta.getState());
        assertEquals(1, registry.getActiveSegments("shard-0").size());
    }
}
