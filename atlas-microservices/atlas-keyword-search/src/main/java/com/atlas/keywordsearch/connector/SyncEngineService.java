package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.Connector;
import com.atlas.domain.connector.ConnectorSyncJob;
import com.atlas.domain.document.UniversalDocument;
import com.atlas.keywordsearch.document.MultiModalDocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * High-throughput Sync Engine streaming connector documents into the central Atlas ingestion pipeline:
 * Connector -> Sync Job -> UniversalDocument -> Parser -> OCR -> Metadata -> Hybrid Index.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SyncEngineService {

    private final ConnectorRegistry connectorRegistry;
    private final MultiModalDocumentService multiModalDocumentService;
    private final SyncJobHistoryStore syncJobHistoryStore;
    private final SyncDeadLetterQueue syncDeadLetterQueue;

    public ConnectorSyncJob executeSyncJob(String connectorId, String syncMode) {
        String jobId = "job-" + UUID.randomUUID().toString().substring(0, 8);
        log.info("[SyncEngineService] Triggering {} sync job '{}' for connector '{}'", syncMode, jobId, connectorId);

        ConnectorSyncJob job = ConnectorSyncJob.builder()
                .jobId(jobId)
                .connectorId(connectorId)
                .syncMode(syncMode != null ? syncMode.toUpperCase() : "FULL")
                .status("RUNNING")
                .startTime(Instant.now())
                .build();

        syncJobHistoryStore.saveJob(job);

        Connector connector = connectorRegistry.getConnector(connectorId);
        if (connector == null) {
            log.error("[SyncEngineService] Connector '{}' not found in registry!", connectorId);
            job.setStatus("FAILED");
            job.setErrorDetails("Connector not registered");
            job.setEndTime(Instant.now());
            syncDeadLetterQueue.enqueueFailedItem(connectorId, jobId, "Unknown", "Connector not registered");
            return job;
        }

        try {
            List<UniversalDocument> docs = connector.sync(job);
            for (UniversalDocument doc : docs) {
                multiModalDocumentService.indexDocument(doc);
            }
            job.setStatus("COMPLETED");
            job.setDocsProcessed(docs.size());
            job.setEndTime(Instant.now());
            log.info("[SyncEngineService] Successfully synced {} documents into Atlas pipeline for connector '{}'", docs.size(), connectorId);
        } catch (Exception e) {
            job.setStatus("FAILED");
            job.setErrorDetails(e.getMessage());
            job.setEndTime(Instant.now());
            syncDeadLetterQueue.enqueueFailedItem(connectorId, jobId, "Sync Error", e.getMessage());
            log.error("[SyncEngineService] Sync execution error: {}", e.getMessage(), e);
        }

        return syncJobHistoryStore.saveJob(job);
    }
}
