package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Health metric status descriptor for enterprise connectors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorHealth {
    private String connectorId;
    private String name;
    private String status; // UP, DOWN, DEGRADED
    private long latencyMs;
    private long lastCheckedTimestamp;
    private String message;
    private int consecutiveFailures;
}
