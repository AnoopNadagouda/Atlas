package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Technical configuration parameters for enterprise connectors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorConfiguration {
    private String connectorId;
    private String name;
    private String endpointUrl;
    private String cronSchedule; // e.g. "0 0/15 * * * ?"
    private int batchSize;
    private int timeoutMs;
    private int maxRetries;
    private boolean incrementalEnabled;
    private boolean autoSyncEnabled;
    private String tenantId;
    private String repositoryName;
    private String workspaceName;

    @Builder.Default
    private Map<String, Object> customProperties = new HashMap<>();
}
