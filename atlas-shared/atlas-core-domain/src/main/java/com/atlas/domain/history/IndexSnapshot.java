package com.atlas.domain.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexSnapshot {
    private String snapshotId;
    private Instant timestamp;
    private long documentCount;
    private long sizeBytes;
    private List<String> segmentIds;
    private String status;
}
