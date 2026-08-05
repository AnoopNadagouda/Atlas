package com.atlas.domain.document.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Performance & execution metrics for document parsers.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParserStatistics {
    private String parserName;
    private long totalDocumentsParsed;
    private long successCount;
    private long failureCount;
    private double avgParsingTimeMs;
    private long totalBytesProcessed;
}
