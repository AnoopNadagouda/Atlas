package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.Connector;
import com.atlas.domain.connector.ConnectorSyncJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Connector Scheduler managing cron schedules, incremental syncs, and manual pause/resume triggers.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorScheduler {

    private final ConnectorRegistry connectorRegistry;
    private final SyncEngineService syncEngineService;
    private final Map<String, Boolean> pausedConnectors = new ConcurrentHashMap<>();

    public void pauseSchedule(String connectorId) {
        pausedConnectors.put(connectorId, true);
        log.info("[ConnectorScheduler] Paused sync schedule for connector '{}'", connectorId);
    }

    public void resumeSchedule(String connectorId) {
        pausedConnectors.put(connectorId, false);
        log.info("[ConnectorScheduler] Resumed sync schedule for connector '{}'", connectorId);
    }

    public boolean isPaused(String connectorId) {
        return pausedConnectors.getOrDefault(connectorId, false);
    }

    @Scheduled(cron = "0 0/15 * * * ?") // Runs every 15 minutes
    public void runScheduledSyncs() {
        log.info("[ConnectorScheduler] Triggering scheduled incremental connector sync pass...");
        for (Connector connector : connectorRegistry.getAllConnectors()) {
            String cid = connector.getMetadata().getConnectorId();
            if (!isPaused(cid)) {
                syncEngineService.executeSyncJob(cid, "INCREMENTAL");
            }
        }
    }
}
