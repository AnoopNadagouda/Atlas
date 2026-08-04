package com.atlas.domain.ranking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageRankScore {
    private String docId;
    private double currentScore;
    private double previousScore;
    private int iteration;
    private boolean converged;
    private Instant lastUpdated;
}
