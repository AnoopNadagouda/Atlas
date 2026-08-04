package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParserStatisticsDto {
    private long totalProcessed;
    private long exactDuplicates;
    private long simhashDuplicates;
    private long canonicalDuplicates;
    private long totalDuplicates;
    private double duplicateRate;
    private long failuresCount;
}
