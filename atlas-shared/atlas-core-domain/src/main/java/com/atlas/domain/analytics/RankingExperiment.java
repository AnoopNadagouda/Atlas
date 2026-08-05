package com.atlas.domain.analytics;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RankingExperiment {
    private String experimentId;
    private String name;
    private int trafficSplitPercent;
    private String activeProfile;
    private String status;
    private Instant createdAt;
}
