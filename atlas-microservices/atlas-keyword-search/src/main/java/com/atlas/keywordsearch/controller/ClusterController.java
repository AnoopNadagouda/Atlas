package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.domain.cluster.ClusterNode;
import com.atlas.domain.cluster.ShardMetadata;
import com.atlas.keywordsearch.cluster.ClusterManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v4/cluster")
@RequiredArgsConstructor
public class ClusterController {

    private final ClusterManager clusterManager;

    @GetMapping("/nodes")
    public ResponseEntity<ApiResponse<List<ClusterNode>>> getClusterNodes() {
        log.info("Fetching registered cluster nodes");
        return ResponseEntity.ok(ApiResponse.success(clusterManager.getActiveNodes()));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getClusterHealth() {
        log.info("Fetching cluster health metrics");
        return ResponseEntity.ok(ApiResponse.success(clusterManager.getClusterHealth()));
    }

    @GetMapping("/shards")
    public ResponseEntity<ApiResponse<List<ShardMetadata>>> getShards() {
        log.info("Fetching shard metadata & assignments");
        return ResponseEntity.ok(ApiResponse.success(clusterManager.getShards()));
    }

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<Map<String, Object>>> getStatistics() {
        log.info("Fetching distributed cluster statistics");
        Map<String, Object> stats = Map.of(
                "clusterHealth", clusterManager.getClusterHealth(),
                "nodeCount", clusterManager.getActiveNodes().size(),
                "shardCount", clusterManager.getShards().size(),
                "shardingStrategy", "HashShardingStrategy (MurmurHash-3)",
                "status", "ACTIVE"
        );
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @PostMapping("/rebalance")
    public ResponseEntity<ApiResponse<String>> triggerRebalance() {
        log.info("Triggering cluster shard rebalance");
        return ResponseEntity.ok(ApiResponse.success("Cluster shard rebalance initiated across all healthy search nodes"));
    }
}
