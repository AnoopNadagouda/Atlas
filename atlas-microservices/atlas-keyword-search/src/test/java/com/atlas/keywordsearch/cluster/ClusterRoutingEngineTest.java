package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.RoutingTableEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClusterRoutingEngineTest {

    private ClusterRoutingEngine routingEngine;

    @BeforeEach
    void setUp() {
        routingEngine = new ClusterRoutingEngine();
        routingEngine.initRoutingTable();
    }

    @Test
    void testRoutingTableAndReplicaPromotion() {
        List<RoutingTableEntry> table = routingEngine.getRoutingTable();
        assertNotNull(table);
        assertEquals(2, table.size());

        String node = routingEngine.selectNodeForRead("shard-0");
        assertEquals("search-node-1", node);

        routingEngine.promoteReplicaToPrimary("shard-0", "search-node-2");

        String newReadNode = routingEngine.selectNodeForRead("shard-0");
        assertEquals("search-node-2", newReadNode);
    }
}
