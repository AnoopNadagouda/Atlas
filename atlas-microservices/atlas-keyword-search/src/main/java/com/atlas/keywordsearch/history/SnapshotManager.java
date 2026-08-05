package com.atlas.keywordsearch.history;

import com.atlas.domain.history.IndexSnapshot;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SnapshotManager {

    private final Map<String, IndexSnapshot> snapshots = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedSnapshots() {
        log.info("Initializing Index Snapshot Manager...");
        Instant now = Instant.now();

        createSnapshot("snap-2026-07-01", now.minus(30, ChronoUnit.DAYS), 250000, 64000000);
        createSnapshot("snap-2026-07-15", now.minus(15, ChronoUnit.DAYS), 350000, 96000000);
        createSnapshot("snap-2026-08-01", now, 500000, 128000000);
    }

    public synchronized IndexSnapshot createSnapshot(String id, Instant timestamp, long docs, long bytes) {
        IndexSnapshot snapshot = IndexSnapshot.builder()
                .snapshotId(id)
                .timestamp(timestamp)
                .documentCount(docs)
                .sizeBytes(bytes)
                .segmentIds(List.of("seg-001", "seg-002"))
                .status("ACTIVE")
                .build();
        snapshots.put(id, snapshot);
        log.info("[SnapshotManager] Created Index Snapshot '{}' (Docs: {}, Size: {} bytes)", id, docs, bytes);
        return snapshot;
    }

    public List<IndexSnapshot> getAllSnapshots() {
        return new ArrayList<>(snapshots.values());
    }

    public IndexSnapshot getNearestSnapshot(Instant timestamp) {
        if (snapshots.isEmpty()) return null;
        IndexSnapshot best = null;
        long minDiff = Long.MAX_VALUE;

        for (IndexSnapshot snap : snapshots.values()) {
            long diff = Math.abs(ChronoUnit.SECONDS.between(snap.getTimestamp(), timestamp));
            if (diff < minDiff) {
                minDiff = diff;
                best = snap;
            }
        }
        return best;
    }
}
