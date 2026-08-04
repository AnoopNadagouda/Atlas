package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexStatisticsDto {
    private long totalDocumentsIndexed;
    private long totalTermsIndexed;
    private long vocabularySize;
    private long totalSegmentsCount;
    private String storagePath;
}
