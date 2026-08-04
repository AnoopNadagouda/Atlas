package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import com.atlas.domain.index.SegmentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundMergeEngine {

    private final SegmentRegistry segmentRegistry;

    public synchronized SegmentMeta mergeSegments(String shardId) {
        List<SegmentMeta> activeSegments = segmentRegistry.getActiveSegments(shardId);
        if (activeSegments.size() < 2) {
            log.info("[BackgroundMergeEngine] Shard '{}' has less than 2 active segments. Merge skipped.", shardId);
            return null;
        }

        log.info("[BackgroundMergeEngine] Initiating background compaction merge for shard '{}' across {} segments",
                shardId, activeSegments.size());

        long totalDocs = activeSegments.stream().mapToLong(SegmentMeta::getDocumentCount).sum();
        long totalSize = activeSegments.stream().mapToLong(SegmentMeta::getSizeBytes).sum();
        List<String> oldSegmentIds = activeSegments.stream().map(SegmentMeta::getSegmentId).collect(Collectors.toList());

        String newSegmentId = "seg-merged-" + UUID.randomUUID().toString().substring(0, 8);
        SegmentMeta newSegment = SegmentMeta.builder()
                .segmentId(newSegmentId)
                .shardId(shardId)
                .documentCount(totalDocs)
                .createdAt(Instant.now())
                .sizeBytes(totalSize)
                .version(2)
                .generation(2)
                .state(SegmentState.ACTIVE)
                .build();

        segmentRegistry.atomicSwapSegments(oldSegmentIds, newSegment);
        log.info("[BackgroundMergeEngine] Background merge completed. Created new consolidated segment '{}'", newSegmentId);
        return newSegment;
    }
}
