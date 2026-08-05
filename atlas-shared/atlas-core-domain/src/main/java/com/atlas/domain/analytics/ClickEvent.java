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
public class ClickEvent {
    private String id;
    private String query;
    private String docId;
    private int clickPosition;
    private long dwellTimeMs;
    private Instant timestamp;
    private String sessionId;
}
