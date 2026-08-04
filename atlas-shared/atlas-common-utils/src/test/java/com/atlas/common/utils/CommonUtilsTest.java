package com.atlas.common.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommonUtilsTest {

    @Test
    void testStringUtils() {
        assertTrue(StringUtils.isNullOrBlank("  "));
        assertEquals("atlas-search-platform", StringUtils.toSlug("Atlas Search Platform!"));
        assertEquals("Hello...", StringUtils.truncate("Hello World", 5));
    }

    @Test
    void testHashUtils() {
        String hash = HashUtils.sha256("test");
        assertNotNull(hash);
        assertEquals(64, hash.length());
        
        long simhash = HashUtils.calculateSimHash64("Atlas distributed search engine platform");
        assertNotEquals(0L, simhash);
    }

    @Test
    void testRegexUtils() {
        assertTrue(RegexUtils.isValidUrl("https://atlas.search/docs"));
        assertEquals("atlas.search", RegexUtils.extractDomain("https://atlas.search/docs/bm25"));
    }

    @Test
    void testCollectionUtils() {
        var list = java.util.List.of(1, 2, 3, 4, 5);
        var partitioned = CollectionUtils.partition(list, 2);
        assertEquals(3, partitioned.size());
        assertEquals(java.util.List.of(1, 2), partitioned.get(0));
    }
}
