package com.atlas.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchQueryExecutedEvent {
    private String query;
    private String intent;
    private long totalHits;
    private long executionTimeMs;
    private boolean cached;
    private Instant executedAt;
}
