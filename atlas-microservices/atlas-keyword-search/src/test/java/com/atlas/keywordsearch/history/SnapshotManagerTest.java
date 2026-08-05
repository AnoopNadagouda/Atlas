package com.atlas.keywordsearch.history;

import com.atlas.domain.history.IndexSnapshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class SnapshotManagerTest {

    private SnapshotManager snapshotManager;

    @BeforeEach
    void setUp() {
        snapshotManager = new SnapshotManager();
        snapshotManager.initSeedSnapshots();
    }

    @Test
    void testNearestSnapshot() {
        IndexSnapshot snap = snapshotManager.getNearestSnapshot(Instant.now());
        assertNotNull(snap);
        assertEquals("snap-2026-08-01", snap.getSnapshotId());
    }
}
