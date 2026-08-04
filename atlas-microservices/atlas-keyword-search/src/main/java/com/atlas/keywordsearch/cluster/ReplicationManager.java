package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.ReplicaStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplicationManager {

    private final ClusterRoutingEngine routingEngine;
    private final Map<String, ReplicaStatus> replicaStatuses = new ConcurrentHashMap<>();

    public void initReplicas() {
        replicaStatuses.put("shard-0-replica-1", ReplicaStatus.ACTIVE);
        replicaStatuses.put("shard-1-replica-1", ReplicaStatus.ACTIVE);
        log.info("[ReplicationManager] Replicas initialized and in ACTIVE sync state");
    }

    public List<Map<String, Object>> getReplicas() {
        if (replicaStatuses.isEmpty()) {
            initReplicas();
        }
        List<Map<String, Object>> list = new ArrayList<>();
        replicaStatuses.forEach((key, status) -> list.add(Map.of(
                "replicaId", key,
                "status", status.toString(),
                "syncLagMs", 2.4,
                "health", "HEALTHY"
        )));
        return list;
    }

    public boolean promoteReplica(String shardId, String replicaNodeId) {
        log.info("[ReplicationManager] Manual/Automatic promotion requested for Shard '{}' -> Node '{}'", shardId, replicaNodeId);
        routingEngine.promoteReplicaToPrimary(shardId, replicaNodeId);
        replicaStatuses.put(shardId + "-replica-1", ReplicaStatus.PROMOTED);
        return true;
    }
}
