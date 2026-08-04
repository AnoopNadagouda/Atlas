package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.RoutingTableEntry;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class ClusterRoutingEngine {

    private final Map<String, RoutingTableEntry> routingTable = new ConcurrentHashMap<>();
    private final AtomicLong epoch = new AtomicLong(1);
    private final AtomicLong version = new AtomicLong(1);

    @PostConstruct
    public void initRoutingTable() {
        log.info("Initializing Atomic Cluster Routing Table...");

        routingTable.put("shard-0", RoutingTableEntry.builder()
                .shardId("shard-0")
                .primaryNodeId("search-node-1")
                .replicaNodeIds(List.of("search-node-2"))
                .epoch(epoch.get())
                .version(version.getAndIncrement())
                .failoverActive(false)
                .build());

        routingTable.put("shard-1", RoutingTableEntry.builder()
                .shardId("shard-1")
                .primaryNodeId("search-node-1")
                .replicaNodeIds(List.of("search-node-2"))
                .epoch(epoch.get())
                .version(version.getAndIncrement())
                .failoverActive(false)
                .build());

        log.info("Cluster Routing Table initialized with {} shard routes (Epoch: {})", routingTable.size(), epoch.get());
    }

    public List<RoutingTableEntry> getRoutingTable() {
        return new ArrayList<>(routingTable.values());
    }

    public String selectNodeForRead(String shardId) {
        RoutingTableEntry entry = routingTable.get(shardId);
        if (entry == null) return "search-node-1";
        if (entry.isFailoverActive() && !entry.getReplicaNodeIds().isEmpty()) {
            return entry.getReplicaNodeIds().get(0);
        }
        return entry.getPrimaryNodeId();
    }

    public synchronized void promoteReplicaToPrimary(String shardId, String newPrimaryNodeId) {
        RoutingTableEntry entry = routingTable.get(shardId);
        if (entry != null) {
            String oldPrimary = entry.getPrimaryNodeId();
            entry.setPrimaryNodeId(newPrimaryNodeId);
            entry.setFailoverActive(true);
            entry.setVersion(version.getAndIncrement());
            log.info("[ClusterRoutingEngine] Atomic Failover Promoted Shard '{}': Old Primary '{}' -> New Primary '{}' (Version: {})",
                    shardId, oldPrimary, newPrimaryNodeId, entry.getVersion());
        }
    }
}
