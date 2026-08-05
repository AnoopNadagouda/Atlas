package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Enterprise Connector Manager coordinating connector lifecycle, registration, authentication,
 * health status checks, and statistics monitoring.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorManager {

    private final ConnectorRegistry connectorRegistry;
    private final ConnectorScheduler connectorScheduler;
    private final SecretRotationService secretRotationService;
    private final SyncEngineService syncEngineService;

    public List<ConnectorMetadata> getAllRegisteredConnectors() {
        return connectorRegistry.getAllMetadata();
    }

    public Connector getConnector(String connectorId) {
        return connectorRegistry.getConnector(connectorId);
    }

    public ConnectorConfiguration registerConnector(ConnectorConfiguration config, String authSecret) {
        log.info("[ConnectorManager] Registering new connector configuration: '{}' ({})", config.getName(), config.getConnectorId());

        if (authSecret != null && !authSecret.isBlank()) {
            secretRotationService.rotateSecrets(config.getConnectorId(), authSecret);
        }

        Connector connector = connectorRegistry.getConnector(config.getConnectorId());
        if (connector != null) {
            ConnectorAuthentication auth = secretRotationService.getAuthentication(config.getConnectorId());
            connector.initialize(config, auth);
            connector.connect();
        }

        return config;
    }

    public boolean removeConnector(String connectorId) {
        log.info("[ConnectorManager] Removing connector '{}'", connectorId);
        Connector connector = connectorRegistry.getConnector(connectorId);
        if (connector != null) {
            connector.disconnect();
            return true;
        }
        return false;
    }

    public ConnectorSyncJob triggerSync(String connectorId, String syncMode) {
        return syncEngineService.executeSyncJob(connectorId, syncMode);
    }

    public void pauseConnector(String connectorId) {
        connectorScheduler.pauseSchedule(connectorId);
    }

    public void resumeConnector(String connectorId) {
        connectorScheduler.resumeSchedule(connectorId);
    }

    public ConnectorHealth checkHealth(String connectorId) {
        Connector connector = connectorRegistry.getConnector(connectorId);
        if (connector != null) {
            return connector.testHealth();
        }
        return ConnectorHealth.builder().connectorId(connectorId).status("DOWN").message("Connector not found").build();
    }

    public ConnectorStatistics getStatistics(String connectorId) {
        Connector connector = connectorRegistry.getConnector(connectorId);
        if (connector != null) {
            return connector.getStatistics();
        }
        return ConnectorStatistics.builder().connectorId(connectorId).build();
    }
}
