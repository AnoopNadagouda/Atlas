package com.atlas.keywordsearch.engine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SnippetGeneratorTest {

    private SnippetGenerator snippetGenerator;

    @BeforeEach
    void setUp() {
        snippetGenerator = new SnippetGenerator();
    }

    @Test
    void testSnippetExtractionAndHighlighting() {
        String text = "Atlas is an enterprise-grade cloud-native search engine built to process billions of documents.";
        Set<String> terms = Set.of("atlas", "search");

        String snippet = snippetGenerator.generateSnippet(text, terms);

        assertNotNull(snippet);
        assertTrue(snippet.contains("<b>Atlas</b>") || snippet.contains("<b>atlas</b>"));
        assertTrue(snippet.contains("<b>search</b>"));
    }
}
