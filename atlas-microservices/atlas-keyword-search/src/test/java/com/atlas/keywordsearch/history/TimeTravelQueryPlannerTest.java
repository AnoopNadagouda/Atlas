package com.atlas.keywordsearch.history;

import com.atlas.common.dto.SearchResultDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeTravelQueryPlannerTest {

    private TimeTravelQueryPlanner queryPlanner;

    @BeforeEach
    void setUp() {
        SnapshotManager snapshotManager = new SnapshotManager();
        snapshotManager.initSeedSnapshots();

        VersionedDocumentStore documentStore = new VersionedDocumentStore();
        documentStore.initSeedHistory();

        queryPlanner = new TimeTravelQueryPlanner(snapshotManager, documentStore);
    }

    @Test
    void testExecuteTimeTravelSearch() {
        List<SearchResultDto> results = queryPlanner.executeTimeTravelSearch("atlas search", Instant.now());
        assertNotNull(results);
        assertFalse(results.isEmpty());
    }
}
