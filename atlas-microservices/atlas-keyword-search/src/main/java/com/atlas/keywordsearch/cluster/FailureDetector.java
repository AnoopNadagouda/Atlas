package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.ClusterNode;

import com.atlas.domain.cluster.NodeStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FailureDetector {

    private final ClusterManager clusterManager;
    private final ReplicationManager replicationManager;

    public void checkClusterHeartbeats() {
        List<ClusterNode> activeNodes = clusterManager.getActiveNodes();
        Instant now = Instant.now();

        for (ClusterNode node : activeNodes) {
            if (node.getLastHeartbeat() != null && now.toEpochMilli() - node.getLastHeartbeat().toEpochMilli() > 15000) {
                log.warn("[FailureDetector] Node '{}' missed heartbeat window (>15s). Marking OFFLINE and triggering automatic failover!", node.getNodeId());
                node.setStatus(NodeStatus.OFFLINE);
                replicationManager.promoteReplica("shard-0", "search-node-2");
            }
        }
    }
}
