package com.atlas.domain.cluster;

public enum ShardStatus {
    INITIALIZING,
    ACTIVE,
    REBALANCING,
    REPLICATING,
    OFFLINE
}
