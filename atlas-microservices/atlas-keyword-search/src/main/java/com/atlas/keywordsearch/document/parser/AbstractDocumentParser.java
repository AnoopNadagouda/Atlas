package com.atlas.keywordsearch.document.parser;

import com.atlas.domain.document.parser.DocumentParser;
import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserHealth;
import com.atlas.domain.document.parser.ParserMetadata;
import com.atlas.domain.document.parser.ParserStatistics;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Base abstract implementation of DocumentParser providing statistics and health tracking.
 */
@Slf4j
public abstract class AbstractDocumentParser implements DocumentParser {

    private final String parserName;
    private final AtomicLong totalParsed = new AtomicLong(0);
    private final AtomicLong successCount = new AtomicLong(0);
    private final AtomicLong failureCount = new AtomicLong(0);
    private final AtomicLong totalDurationMs = new AtomicLong(0);
    private final AtomicLong totalBytesProcessed = new AtomicLong(0);

    protected AbstractDocumentParser(String parserName) {
        this.parserName = parserName;
    }

    @Override
    public ExtractionResult extract(InputStream stream, ParserMetadata metadata) {
        long startTime = System.currentTimeMillis();
        totalParsed.incrementAndGet();
        if (metadata != null) {
            totalBytesProcessed.addAndGet(metadata.getSizeBytes());
        }

        try {
            ExtractionResult result = doExtract(stream, metadata);
            long duration = System.currentTimeMillis() - startTime;
            totalDurationMs.addAndGet(duration);
            successCount.incrementAndGet();
            result.setExtractionDurationMs(duration);
            log.info("[{}] Successfully parsed '{}' in {} ms", parserName, metadata != null ? metadata.getFilename() : "stream", duration);
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            totalDurationMs.addAndGet(duration);
            failureCount.incrementAndGet();
            log.error("[{}] Failed to parse document: {}", parserName, e.getMessage(), e);
            return ExtractionResult.builder()
                    .documentTitle(metadata != null ? metadata.getFilename() : "Unknown Document")
                    .status("FAILED")
                    .extractionDurationMs(duration)
                    .extractedText("")
                    .build();
        }
    }

    protected abstract ExtractionResult doExtract(InputStream stream, ParserMetadata metadata) throws Exception;

    @Override
    public ParserHealth checkHealth() {
        return ParserHealth.builder()
                .parserName(parserName)
                .status("UP")
                .message("Parser operating normally")
                .lastCheckedTimestamp(System.currentTimeMillis())
                .build();
    }

    @Override
    public ParserStatistics getStatistics() {
        long parsed = totalParsed.get();
        double avgTime = parsed > 0 ? (double) totalDurationMs.get() / parsed : 0.0;
        return ParserStatistics.builder()
                .parserName(parserName)
                .totalDocumentsParsed(parsed)
                .successCount(successCount.get())
                .failureCount(failureCount.get())
                .avgParsingTimeMs(avgTime)
                .totalBytesProcessed(totalBytesProcessed.get())
                .build();
    }
}
