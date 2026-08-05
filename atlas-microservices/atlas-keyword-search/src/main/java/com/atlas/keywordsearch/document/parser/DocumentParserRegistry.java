package com.atlas.keywordsearch.document.parser;

import com.atlas.domain.document.parser.DocumentParser;
import com.atlas.domain.document.parser.ExtractionResult;
import com.atlas.domain.document.parser.ParserHealth;
import com.atlas.domain.document.parser.ParserMetadata;
import com.atlas.domain.document.parser.ParserStatistics;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Central registry managing all registered DocumentParser SPI instances.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentParserRegistry {

    private final List<DocumentParser> parsers;

    public DocumentParser getParser(String fileType, String mimeType) {
        if (parsers == null || parsers.isEmpty()) {
            log.warn("No DocumentParser instances injected in registry!");
            return null;
        }

        for (DocumentParser parser : parsers) {
            if (parser.supports(fileType, mimeType)) {
                return parser;
            }
        }

        log.info("No dedicated parser found for fileType '{}', mimeType '{}'. Falling back to PlainTextParser.", fileType, mimeType);
        return parsers.stream()
                .filter(p -> p instanceof PlainTextParser)
                .findFirst()
                .orElse(parsers.get(0));
    }

    public ExtractionResult parse(InputStream stream, ParserMetadata metadata) {
        String fileType = metadata != null ? metadata.getFileType() : "TXT";
        String mimeType = metadata != null ? metadata.getMimeType() : "text/plain";
        DocumentParser parser = getParser(fileType, mimeType);
        return parser.extract(stream, metadata);
    }

    public Map<String, ParserHealth> getHealthAll() {
        Map<String, ParserHealth> healthMap = new HashMap<>();
        if (parsers != null) {
            for (DocumentParser p : parsers) {
                ParserHealth health = p.checkHealth();
                healthMap.put(health.getParserName(), health);
            }
        }
        return healthMap;
    }

    public Map<String, ParserStatistics> getStatisticsAll() {
        Map<String, ParserStatistics> statsMap = new HashMap<>();
        if (parsers != null) {
            for (DocumentParser p : parsers) {
                ParserStatistics stats = p.getStatistics();
                statsMap.put(stats.getParserName(), stats);
            }
        }
        return statsMap;
    }

    public int getSupportedParserCount() {
        return parsers != null ? parsers.size() : 0;
    }
}
