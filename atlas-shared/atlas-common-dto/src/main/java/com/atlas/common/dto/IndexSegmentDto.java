package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexSegmentDto {
    private String segmentId;
    private String segmentName;
    private long documentCount;
    private long vocabularySize;
    private long totalTermCount;
    private String storagePath;
    private String status;
    private Instant createdAt;
}
