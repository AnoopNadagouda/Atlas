package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * Synchronization execution job descriptor.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorSyncJob {
    private String jobId;
    private String connectorId;
    private String syncMode; // FULL, INCREMENTAL, WEBHOOK
    private String status; // QUEUED, RUNNING, COMPLETED, FAILED, PAUSED
    private Instant startTime;
    private Instant endTime;
    private long docsProcessed;
    private long docsFailed;
    private String checkpointToken;
    private String errorDetails;
}
