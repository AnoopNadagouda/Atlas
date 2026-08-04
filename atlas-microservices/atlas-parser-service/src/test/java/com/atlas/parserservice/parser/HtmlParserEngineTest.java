package com.atlas.parserservice.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HtmlParserEngineTest {

    private HtmlParserEngine parserEngine;

    @BeforeEach
    void setUp() {
        parserEngine = new HtmlParserEngine();
    }

    @Test
    void testHtmlParsingAndBoilerplateRemoval() {
        String rawHtml = """
                <!DOCTYPE html>
                <html lang="en">
                <head>
                    <title>Atlas Search Infrastructure Docs</title>
                    <meta name="description" content="Distributed search engine architecture documentation.">
                    <link rel="canonical" href="https://atlas.search/docs/arch">
                </head>
                <body>
                    <header class="nav"><a href="/home">Home</a></header>
                    <nav>Menu links</nav>
                    <main>
                        <h1>Architectural Overview</h1>
                        <p>Atlas is a cloud-native distributed AI search engine built for 1B+ scale.</p>
                        <h2>Core Pipeline</h2>
                        <p>Web crawling feeds raw HTML into Kafka topics for parsing.</p>
                        <a href="https://example.com/external-ref">External Reference</a>
                    </main>
                    <aside class="sidebar">Ads and widgets</aside>
                    <footer class="footer">Copyright 2026</footer>
                    <script>console.log("tracking");</script>
                </body>
                </html>
                """;

        HtmlParserEngine.ParsedHtmlData result = parserEngine.parse(rawHtml, "https://atlas.search/docs/arch");

        assertEquals("Atlas Search Infrastructure Docs", result.getTitle());
        assertEquals("Distributed search engine architecture documentation.", result.getDescription());
        assertEquals("https://atlas.search/docs/arch", result.getCanonicalUrl());
        assertEquals("en", result.getHtmlLang());

        // Headings
        assertEquals(2, result.getHeadings().size());
        assertTrue(result.getHeadings().contains("Architectural Overview"));
        assertTrue(result.getHeadings().contains("Core Pipeline"));

        // Clean text - boilerplate nav/footer/script removed
        assertFalse(result.getCleanText().contains("Copyright 2026"));
        assertFalse(result.getCleanText().contains("tracking"));
        assertTrue(result.getCleanText().contains("Atlas is a cloud-native distributed AI search engine"));

        // Links
        assertEquals(2, result.getLinks().size());
    }
}
