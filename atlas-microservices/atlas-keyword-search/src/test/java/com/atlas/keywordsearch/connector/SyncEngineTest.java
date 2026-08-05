package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorSyncJob;
import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import com.atlas.keywordsearch.document.MultiModalDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SyncEngineTest {

    private SyncEngineService syncEngineService;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(new GitHubConnector()));
        registry.registerInjectedConnectors();
        MultiModalDocumentService documentService = new MultiModalDocumentService();
        documentService.initSeedDocuments();
        SyncJobHistoryStore historyStore = new SyncJobHistoryStore();
        SyncDeadLetterQueue dlq = new SyncDeadLetterQueue();

        syncEngineService = new SyncEngineService(registry, documentService, historyStore, dlq);
    }

    @Test
    void testExecuteSyncJob() {
        ConnectorSyncJob job = syncEngineService.executeSyncJob("github", "FULL");
        assertNotNull(job);
        assertEquals("COMPLETED", job.getStatus());
        assertTrue(job.getDocsProcessed() >= 1);
    }
}
