package com.atlas.domain.cluster;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShardMetadata {
    private String shardId;
    private String assignedNodeId;
    private boolean isPrimary;
    private ShardStatus status;
    private long documentCount;
    private int segmentCount;
    private long sizeBytes;
}
