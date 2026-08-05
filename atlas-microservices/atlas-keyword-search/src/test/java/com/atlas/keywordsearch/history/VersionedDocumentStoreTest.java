package com.atlas.keywordsearch.history;

import com.atlas.domain.history.DocumentVersion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VersionedDocumentStoreTest {

    private VersionedDocumentStore documentStore;

    @BeforeEach
    void setUp() {
        documentStore = new VersionedDocumentStore();
        documentStore.initSeedHistory();
    }

    @Test
    void testGetDocumentHistory() {
        List<DocumentVersion> history = documentStore.getDocumentHistory("doc-foundation-001");
        assertNotNull(history);
        assertEquals(3, history.size());
    }

    @Test
    void testGetVersionAtTimestamp() {
        DocumentVersion version = documentStore.getVersionAtTimestamp("doc-foundation-001", Instant.now());
        assertNotNull(version);
        assertEquals("doc-foundation-001", version.getDocId());
    }
}
