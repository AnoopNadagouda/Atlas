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
public class SearchEvent {
    private String id;
    private String query;
    private Instant timestamp;
    private long latencyMs;
    private String userId;
    private String retrievalMode;
    private int resultCount;
    private String clickedDocId;
    private int clickedPosition;
    private String sessionId;
}
