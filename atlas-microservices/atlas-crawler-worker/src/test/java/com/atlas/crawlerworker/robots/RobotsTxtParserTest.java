package com.atlas.crawlerworker.robots;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RobotsTxtParserTest {

    @Test
    void testRobotsTxtParsingAndRules() {
        String content = """
                User-agent: *
                Disallow: /admin/
                Disallow: /private/
                Allow: /admin/public/
                Sitemap: https://example.com/sitemap.xml
                """;

        RobotsTxtParser parser = RobotsTxtParser.parse(content);
        assertFalse(parser.isAllowed("AtlasBot", "/admin/settings"));
        assertTrue(parser.isAllowed("AtlasBot", "/admin/public/page"));
        assertTrue(parser.isAllowed("AtlasBot", "/search"));
        assertEquals(1, parser.getSitemaps().size());
        assertEquals("https://example.com/sitemap.xml", parser.getSitemaps().get(0));
    }
}
