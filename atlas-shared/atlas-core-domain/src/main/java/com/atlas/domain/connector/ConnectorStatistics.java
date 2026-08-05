package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Performance counters and sync statistics for connectors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorStatistics {
    private String connectorId;
    private long totalSyncJobs;
    private long totalDocsSynced;
    private long failedDocsCount;
    private double avgSyncDurationMs;
    private long lastSyncTimestamp;
    private int queueDepth;
    private double throughputPerSec;
}
