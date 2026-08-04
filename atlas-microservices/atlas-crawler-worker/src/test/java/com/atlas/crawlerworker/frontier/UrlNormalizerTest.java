package com.atlas.crawlerworker.frontier;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlNormalizerTest {

    @Test
    void testUrlNormalization() {
        assertEquals("https://atlas.search/docs", UrlNormalizer.normalize("HTTPS://ATLAS.SEARCH/docs#section-1"));
        assertEquals("http://example.com/path", UrlNormalizer.normalize("http://example.com:80/path/"));
        assertEquals("https://example.com/a/b", UrlNormalizer.normalize("https://example.com//a///b"));
    }

    @Test
    void testResolveRelative() {
        assertEquals("https://example.com/docs/api", UrlNormalizer.resolveRelative("https://example.com/docs/index.html", "api"));
        assertEquals("https://example.com/about", UrlNormalizer.resolveRelative("https://example.com/docs/index.html", "/about"));
    }
}
