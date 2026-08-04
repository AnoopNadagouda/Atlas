package com.atlas.domain.index;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SegmentMeta {
    private String segmentId;
    private String shardId;
    private long documentCount;
    private Instant createdAt;
    private long sizeBytes;
    private long version;
    private int generation;
    private SegmentState state;
}
