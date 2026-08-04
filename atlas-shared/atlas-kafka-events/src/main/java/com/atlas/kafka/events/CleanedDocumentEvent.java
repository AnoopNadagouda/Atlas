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
public class CleanedDocumentEvent {
    private String docId;
    private String url;
    private String title;
    private String cleanedContent;
    private long simhash;
    private String domain;
    private Instant cleanedAt;
}
