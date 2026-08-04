package com.atlas.keywordsearch.cluster;

import java.util.List;

public interface ShardingStrategy {

    String targetShardId(String key, int totalShards);

    List<String> getAllShards(int totalShards);
}
