package com.atlas.domain.cluster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClusterNode {
    private String nodeId;
    private String host;
    private int port;
    private NodeStatus status;
    private double cpuUsagePercent;
    private double memoryUsagePercent;
    private double diskUsagePercent;
    private int shardCount;
    private int segmentCount;
    private Instant lastHeartbeat;
}
