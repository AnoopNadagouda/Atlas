package com.atlas.indexbuilder.engine.index;

import com.atlas.domain.index.SegmentMeta;
import com.atlas.domain.index.SegmentState;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class SegmentRegistry {

    private final Map<String, SegmentMeta> segments = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedSegments() {
        registerSegment(SegmentMeta.builder()
                .segmentId("seg-001")
                .shardId("shard-0")
                .documentCount(250000)
                .createdAt(Instant.now())
                .sizeBytes(64000000)
                .version(1)
                .generation(1)
                .state(SegmentState.ACTIVE)
                .build());

        registerSegment(SegmentMeta.builder()
                .segmentId("seg-002")
                .shardId("shard-0")
                .documentCount(250000)
                .createdAt(Instant.now())
                .sizeBytes(64000000)
                .version(1)
                .generation(1)
                .state(SegmentState.ACTIVE)
                .build());

        log.info("[SegmentRegistry] Initialized with {} seed segments", segments.size());
    }

    public void registerSegment(SegmentMeta meta) {
        if (meta == null || meta.getSegmentId() == null) return;
        segments.put(meta.getSegmentId(), meta);
        log.info("[SegmentRegistry] Registered segment '{}' (Shard: '{}', State: {})",
                meta.getSegmentId(), meta.getShardId(), meta.getState());
    }

    public List<SegmentMeta> getActiveSegments(String shardId) {
        List<SegmentMeta> list = new ArrayList<>();
        for (SegmentMeta meta : segments.values()) {
            if (meta.getShardId().equals(shardId) && meta.getState() == SegmentState.ACTIVE) {
                list.add(meta);
            }
        }
        return list;
    }

    public List<SegmentMeta> getAllSegments() {
        return new ArrayList<>(segments.values());
    }

    public synchronized void atomicSwapSegments(List<String> obsoleteSegmentIds, SegmentMeta newMergedSegment) {
        for (String id : obsoleteSegmentIds) {
            SegmentMeta meta = segments.get(id);
            if (meta != null) {
                meta.setState(SegmentState.OBSOLETE);
            }
        }
        registerSegment(newMergedSegment);
        log.info("[SegmentRegistry] Atomic Swap completed: Merged {} segments into new segment '{}'",
                obsoleteSegmentIds.size(), newMergedSegment.getSegmentId());
    }
}
