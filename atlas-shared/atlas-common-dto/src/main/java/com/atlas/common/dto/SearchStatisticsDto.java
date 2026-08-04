package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchStatisticsDto {
    private long totalQueriesExecuted;
    private double averageLatencyMs;
    private long cacheHits;
    private long cacheMisses;
    private double cacheHitRatio;
    private long totalDocumentsInCollection;
}
