package com.atlas.keywordsearch.cluster;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HashShardingStrategyTest {

    private HashShardingStrategy shardingStrategy;

    @BeforeEach
    void setUp() {
        shardingStrategy = new HashShardingStrategy();
    }

    @Test
    void testTargetShardIdAndGetAllShards() {
        String shard = shardingStrategy.targetShardId("doc-100", 4);
        assertNotNull(shard);
        assertTrue(shard.startsWith("shard-"));

        List<String> allShards = shardingStrategy.getAllShards(4);
        assertEquals(4, allShards.size());
        assertTrue(allShards.contains("shard-0"));
        assertTrue(allShards.contains("shard-3"));
    }
}
