package com.atlas.domain.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityMetrics {
    private double ndcgAt10;
    private double precisionAt10;
    private double recallAt10;
    private double mrr;
    private double mapScore;
    private double ctr;
    private double zeroResultRate;
    private double avgLatencyMs;
    private double avgRankingScore;
}
