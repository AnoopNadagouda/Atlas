package com.atlas.keywordsearch.copilot;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasCopilotProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContextBuilderTest {

    private ContextBuilder contextBuilder;

    @BeforeEach
    void setUp() {
        AtlasCopilotProperties properties = new AtlasCopilotProperties();
        properties.setMaxContextTokens(2048);
        contextBuilder = new ContextBuilder(properties);
    }

    @Test
    void testContextBuildingAndDeduplication() {
        SearchResultDto doc1 = SearchResultDto.builder()
                .id("doc-1")
                .title("Atlas Foundation")
                .url("https://atlas.search/1")
                .snippet("Phase 1 foundation active.")
                .build();

        SearchResultDto doc2 = SearchResultDto.builder()
                .id("doc-1") // Duplicate
                .title("Atlas Foundation Duplicate")
                .url("https://atlas.search/1")
                .snippet("Phase 1 foundation active.")
                .build();

        SearchResultDto doc3 = SearchResultDto.builder()
                .id("doc-2")
                .title("BM25 Specification")
                .url("https://atlas.search/2")
                .snippet("Robertson-Spärck Jones ranker.")
                .build();

        String context = contextBuilder.buildContext(List.of(doc1, doc2, doc3));

        assertNotNull(context);
        assertTrue(context.contains("[1] Document ID: doc-1"));
        assertTrue(context.contains("[2] Document ID: doc-2"));
        assertFalse(context.contains("[3]")); // Duplicate was excluded
    }
}
