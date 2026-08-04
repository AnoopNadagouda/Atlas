package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import com.atlas.domain.index.SegmentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SegmentRegistryTest {

    private SegmentRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new SegmentRegistry();
        registry.initSeedSegments();
    }

    @Test
    void testActiveSegmentsAndAtomicSwap() {
        List<SegmentMeta> active = registry.getActiveSegments("shard-0");
        assertEquals(2, active.size());

        SegmentMeta mergedSegment = SegmentMeta.builder()
                .segmentId("seg-merged-999")
                .shardId("shard-0")
                .documentCount(500000)
                .createdAt(Instant.now())
                .sizeBytes(128000000)
                .version(2)
                .generation(2)
                .state(SegmentState.ACTIVE)
                .build();

        registry.atomicSwapSegments(List.of("seg-001", "seg-002"), mergedSegment);

        List<SegmentMeta> newActive = registry.getActiveSegments("shard-0");
        assertEquals(1, newActive.size());
        assertEquals("seg-merged-999", newActive.get(0).getSegmentId());
    }
}
