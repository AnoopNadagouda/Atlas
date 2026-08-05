package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.connector.*;
import com.atlas.keywordsearch.connector.ConnectorManager;
import com.atlas.keywordsearch.connector.SyncDeadLetterQueue;
import com.atlas.keywordsearch.connector.SyncJobHistoryStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Production REST API Controller for Enterprise Connectors & Federation Framework.
 * Hosted under /api/v17/connectors with 100% backward compatibility.
 */
@Slf4j
@RestController
@RequestMapping("/api/v17/connectors")
@RequiredArgsConstructor
public class ConnectorController {

    private final ConnectorManager connectorManager;
    private final SyncJobHistoryStore syncJobHistoryStore;
    private final SyncDeadLetterQueue syncDeadLetterQueue;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ConnectorMetadata>>> getAllConnectors() {
        log.info("Fetching all registered enterprise connectors");
        return ResponseEntity.ok(ApiResponse.success(connectorManager.getAllRegisteredConnectors()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ConnectorConfiguration>> registerConnector(
            @RequestBody ConnectorConfiguration config,
            @RequestParam(required = false) String authSecret) {
        log.info("Registering new enterprise connector: '{}' ({})", config.getName(), config.getConnectorId());
        ConnectorConfiguration registered = connectorManager.registerConnector(config, authSecret);
        return ResponseEntity.ok(ApiResponse.success(registered));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> removeConnector(@PathVariable String id) {
        log.info("Removing connector: '{}'", id);
        boolean removed = connectorManager.removeConnector(id);
        return ResponseEntity.ok(ApiResponse.success(removed));
    }

    @PostMapping("/{id}/sync")
    public ResponseEntity<ApiResponse<ConnectorSyncJob>> triggerSync(
            @PathVariable String id,
            @RequestParam(defaultValue = "FULL") String syncMode) {
        log.info("Triggering {} sync job for connector: '{}'", syncMode, id);
        ConnectorSyncJob job = connectorManager.triggerSync(id, syncMode);
        return ResponseEntity.ok(ApiResponse.success(job));
    }

    @PostMapping("/{id}/pause")
    public ResponseEntity<ApiResponse<String>> pauseConnector(@PathVariable String id) {
        log.info("Pausing sync schedule for connector: '{}'", id);
        connectorManager.pauseConnector(id);
        return ResponseEntity.ok(ApiResponse.success("Connector schedule paused successfully"));
    }

    @PostMapping("/{id}/resume")
    public ResponseEntity<ApiResponse<String>> resumeConnector(@PathVariable String id) {
        log.info("Resuming sync schedule for connector: '{}'", id);
        connectorManager.resumeConnector(id);
        return ResponseEntity.ok(ApiResponse.success("Connector schedule resumed successfully"));
    }

    @GetMapping("/{id}/health")
    public ResponseEntity<ApiResponse<ConnectorHealth>> getHealth(@PathVariable String id) {
        log.info("Checking health status for connector: '{}'", id);
        ConnectorHealth health = connectorManager.checkHealth(id);
        return ResponseEntity.ok(ApiResponse.success(health));
    }

    @GetMapping("/{id}/statistics")
    public ResponseEntity<ApiResponse<ConnectorStatistics>> getStatistics(@PathVariable String id) {
        log.info("Fetching statistics for connector: '{}'", id);
        ConnectorStatistics stats = connectorManager.getStatistics(id);
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/jobs")
    public ResponseEntity<ApiResponse<List<ConnectorSyncJob>>> getAllSyncJobs() {
        log.info("Fetching sync job execution history");
        return ResponseEntity.ok(ApiResponse.success(syncJobHistoryStore.getAllJobs()));
    }

    @GetMapping("/jobs/{id}")
    public ResponseEntity<ApiResponse<ConnectorSyncJob>> getSyncJob(@PathVariable String id) {
        log.info("Fetching details for sync job: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(syncJobHistoryStore.getJob(id)));
    }

    @GetMapping("/dlq")
    public ResponseEntity<ApiResponse<List<SyncDeadLetterQueue.DlqMessage>>> getDlqMessages() {
        log.info("Fetching Dead-Letter Queue (DLQ) failed sync messages");
        return ResponseEntity.ok(ApiResponse.success(syncDeadLetterQueue.getAllMessages()));
    }
}
