package com.atlas.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmbeddedDocumentEvent {
    private String documentId;
    private String normalizedUrl;
    private String modelName;
    private int dimension;
    private float[] vector;
    private Map<String, Object> metadata;
    private Instant embeddedAt;
}
