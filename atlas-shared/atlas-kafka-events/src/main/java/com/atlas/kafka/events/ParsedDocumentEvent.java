package com.atlas.kafka.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedDocumentEvent {
    private String url;
    private String title;
    private String extractedText;
    private List<String> outboundLinks;
    private Map<String, String> metaTags;
    private Instant parsedAt;
}
