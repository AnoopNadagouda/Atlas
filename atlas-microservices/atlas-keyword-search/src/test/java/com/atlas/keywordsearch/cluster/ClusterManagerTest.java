package com.atlas.keywordsearch.cluster;

import com.atlas.domain.cluster.ClusterNode;
import com.atlas.domain.cluster.ShardMetadata;
import com.atlas.keywordsearch.config.AtlasClusterProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ClusterManagerTest {

    private ClusterManager clusterManager;

    @BeforeEach
    void setUp() {
        AtlasClusterProperties properties = new AtlasClusterProperties();
        properties.setNodeId("test-node-1");
        properties.setHost("localhost");
        properties.setPort(8082);

        clusterManager = new ClusterManager(properties);
        clusterManager.registerLocalNode();
    }

    @Test
    void testNodeRegistrationAndHealth() {
        List<ClusterNode> nodes = clusterManager.getActiveNodes();
        assertNotNull(nodes);
        assertEquals(1, nodes.size());
        assertEquals("test-node-1", nodes.get(0).getNodeId());

        List<ShardMetadata> shards = clusterManager.getShards();
        assertEquals(2, shards.size());

        Map<String, Object> health = clusterManager.getClusterHealth();
        assertEquals("GREEN", health.get("status"));
    }
}
