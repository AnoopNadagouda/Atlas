package com.atlas.keywordsearch.query;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class QueryParserTest {

    private QueryParser parser;

    @BeforeEach
    void setUp() {
        QueryNormalizer normalizer = new QueryNormalizer();
        parser = new QueryParser(normalizer);
    }

    @Test
    void testQueryParsingAndBooleanOperators() {
        String queryStr = "cloud AND search NOT legacy \"distributed system\"";
        ParsedQuery parsed = parser.parse(queryStr);

        assertNotNull(parsed);
        assertFalse(parsed.getPhrases().isEmpty());
        assertTrue(parsed.getMustTerms().contains("search"));
        assertTrue(parsed.getMustNotTerms().contains("legacy"));
        assertTrue(parsed.getNormalizedTerms().contains("cloud"));
    }
}
