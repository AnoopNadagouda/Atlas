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
public class CrawlSeedEvent {
    private String jobId;
    private String url;
    private String domain;
    private int depth;
    @Builder.Default
    private Instant timestamp = Instant.now();
}
