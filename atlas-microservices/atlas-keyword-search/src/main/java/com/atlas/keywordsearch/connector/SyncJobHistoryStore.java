package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorSyncJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Persistent sync job history repository store.
 */
@Slf4j
@Repository
public class SyncJobHistoryStore {

    private final Map<String, ConnectorSyncJob> jobStore = new ConcurrentHashMap<>();

    public ConnectorSyncJob saveJob(ConnectorSyncJob job) {
        if (job != null && job.getJobId() != null) {
            jobStore.put(job.getJobId(), job);
        }
        return job;
    }

    public ConnectorSyncJob getJob(String jobId) {
        return jobStore.get(jobId);
    }

    public List<ConnectorSyncJob> getJobsForConnector(String connectorId) {
        return jobStore.values().stream()
                .filter(j -> j.getConnectorId().equalsIgnoreCase(connectorId))
                .collect(Collectors.toList());
    }

    public List<ConnectorSyncJob> getAllJobs() {
        return new ArrayList<>(jobStore.values());
    }
}
