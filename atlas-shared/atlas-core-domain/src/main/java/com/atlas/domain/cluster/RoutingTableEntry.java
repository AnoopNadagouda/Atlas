package com.atlas.domain.cluster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoutingTableEntry {
    private String shardId;
    private String primaryNodeId;
    private List<String> replicaNodeIds;
    private long epoch;
    private long version;
    private boolean failoverActive;
}
