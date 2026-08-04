package com.atlas.crawlerworker.sitemap;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SitemapParserTest {

    @Test
    void testExtractUrlsFromXml() {
        String xml = """
                <?xml version="1.0" encoding="UTF-8"?>
                <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                   <url>
                      <loc>https://example.com/</loc>
                   </url>
                   <url>
                      <loc>https://example.com/docs/api</loc>
                   </url>
                </urlset>
                """;

        SitemapParser parser = new SitemapParser();
        List<String> urls = parser.extractUrls(xml);
        assertEquals(2, urls.size());
        assertTrue(urls.contains("https://example.com/"));
        assertTrue(urls.contains("https://example.com/docs/api"));
    }
}
