package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexStatusResponse {
    private long totalDocumentsIndexed;
    private long totalSegments;
    private long indexSizeBytes;
    private String status;
}
