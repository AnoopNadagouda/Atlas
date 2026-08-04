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
public class IndexBuildTaskEvent {
    private String docId;
    private String url;
    private String title;
    private String content;
    private String shardId;
    private Instant queuedAt;
}
