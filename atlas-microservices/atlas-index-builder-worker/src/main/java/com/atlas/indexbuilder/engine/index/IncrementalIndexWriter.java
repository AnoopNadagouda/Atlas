package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import com.atlas.domain.index.SegmentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IncrementalIndexWriter {

    private final SegmentRegistry segmentRegistry;

    public SegmentMeta writeIncrementalSegment(String shardId, long docCount, long sizeBytes) {
        String segmentId = "seg-inc-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("[IncrementalIndexWriter] Writing new immutable incremental segment '{}' for shard '{}' (DocCount: {})",
                segmentId, shardId, docCount);

        SegmentMeta meta = SegmentMeta.builder()
                .segmentId(segmentId)
                .shardId(shardId)
                .documentCount(docCount)
                .createdAt(Instant.now())
                .sizeBytes(sizeBytes)
                .version(1)
                .generation(1)
                .state(SegmentState.ACTIVE)
                .build();

        segmentRegistry.registerSegment(meta);
        return meta;
    }
}
