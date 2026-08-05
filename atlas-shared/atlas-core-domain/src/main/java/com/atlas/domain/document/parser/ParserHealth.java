package com.atlas.domain.document.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Health check status descriptor for document parsers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParserHealth {
    private String parserName;
    private String status; // UP, DOWN, DEGRADED
    private String message;
    private long lastCheckedTimestamp;
}
