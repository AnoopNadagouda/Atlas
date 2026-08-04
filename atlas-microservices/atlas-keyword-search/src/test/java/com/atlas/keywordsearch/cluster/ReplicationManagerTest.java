package com.atlas.keywordsearch.cluster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ReplicationManagerTest {

    private ReplicationManager replicationManager;

    @BeforeEach
    void setUp() {
        ClusterRoutingEngine routingEngine = new ClusterRoutingEngine();
        routingEngine.initRoutingTable();
        replicationManager = new ReplicationManager(routingEngine);
    }

    @Test
    void testReplicaListingAndPromotion() {
        List<Map<String, Object>> replicas = replicationManager.getReplicas();
        assertNotNull(replicas);
        assertFalse(replicas.isEmpty());

        boolean promoted = replicationManager.promoteReplica("shard-0", "search-node-2");
        assertTrue(promoted);
    }
}
