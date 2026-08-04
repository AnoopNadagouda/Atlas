package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.ClusterNode;
import com.atlas.domain.cluster.NodeStatus;
import com.atlas.domain.cluster.ShardMetadata;
import com.atlas.domain.cluster.ShardStatus;
import com.atlas.keywordsearch.config.AtlasClusterProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterManager {

    private final AtlasClusterProperties clusterProperties;
    private final Map<String, ClusterNode> nodes = new ConcurrentHashMap<>();
    private final Map<String, ShardMetadata> shards = new ConcurrentHashMap<>();

    @PostConstruct
    public void registerLocalNode() {
        log.info("Initializing ClusterManager for NodeId: '{}' ({}:{})",
                clusterProperties.getNodeId(), clusterProperties.getHost(), clusterProperties.getPort());

        ClusterNode localNode = ClusterNode.builder()
                .nodeId(clusterProperties.getNodeId())
                .host(clusterProperties.getHost())
                .port(clusterProperties.getPort())
                .status(NodeStatus.HEALTHY)
                .cpuUsagePercent(12.5)
                .memoryUsagePercent(34.2)
                .diskUsagePercent(18.9)
                .shardCount(2)
                .segmentCount(4)
                .lastHeartbeat(Instant.now())
                .build();

        nodes.put(localNode.getNodeId(), localNode);

        // Register default shards
        shards.put("shard-0", ShardMetadata.builder()
                .shardId("shard-0")
                .assignedNodeId(clusterProperties.getNodeId())
                .isPrimary(true)
                .status(ShardStatus.ACTIVE)
                .documentCount(500000)
                .segmentCount(2)
                .sizeBytes(128000000)
                .build());

        shards.put("shard-1", ShardMetadata.builder()
                .shardId("shard-1")
                .assignedNodeId(clusterProperties.getNodeId())
                .isPrimary(true)
                .status(ShardStatus.ACTIVE)
                .documentCount(500000)
                .segmentCount(2)
                .sizeBytes(128000000)
                .build());

        log.info("ClusterManager initialized with {} node and {} active shards", nodes.size(), shards.size());
    }

    public List<ClusterNode> getActiveNodes() {
        return new ArrayList<>(nodes.values());
    }

    public List<ShardMetadata> getShards() {
        return new ArrayList<>(shards.values());
    }

    public Map<String, Object> getClusterHealth() {
        long healthyCount = nodes.values().stream().filter(n -> n.getStatus() == NodeStatus.HEALTHY).count();
        return Map.of(
                "clusterName", "atlas-search-cluster",
                "status", healthyCount == nodes.size() ? "GREEN" : "YELLOW",
                "totalNodes", nodes.size(),
                "healthyNodes", healthyCount,
                "totalShards", shards.size(),
                "activeShards", shards.size(),
                "rebalancing", false
        );
    }
}
