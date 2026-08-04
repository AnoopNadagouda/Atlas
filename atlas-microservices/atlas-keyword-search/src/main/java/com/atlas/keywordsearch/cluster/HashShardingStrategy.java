package com.atlas.keywordsearch.cluster;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HashShardingStrategy implements ShardingStrategy {

    @Override
    public String targetShardId(String key, int totalShards) {
        if (key == null || totalShards <= 0) return "shard-0";
        int hash = Math.abs(key.hashCode());
        int shardIndex = hash % totalShards;
        return "shard-" + shardIndex;
    }

    @Override
    public List<String> getAllShards(int totalShards) {
        List<String> shards = new ArrayList<>();
        int count = Math.max(1, totalShards);
        for (int i = 0; i < count; i++) {
            shards.add("shard-" + i);
        }
        return shards;
    }
}
