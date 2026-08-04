package com.atlas.keywordsearch.ranking;

import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
public class FreshnessScorer {

    private static final double DECAY_LAMBDA = 0.05; // Decay rate per day

    public double calculateFreshnessScore(Instant lastCrawlTime) {
        if (lastCrawlTime == null) return 0.5;
        long daysOld = ChronoUnit.DAYS.between(lastCrawlTime, Instant.now());
        if (daysOld < 0) daysOld = 0;

        return Math.exp(-DECAY_LAMBDA * daysOld);
    }
}
