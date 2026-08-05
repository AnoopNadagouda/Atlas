package com.atlas.keywordsearch.document;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ThumbnailServiceTest {

    private ThumbnailService thumbnailService;

    @BeforeEach
    void setUp() {
        thumbnailService = new ThumbnailService();
    }

    @Test
    void testGenerateThumbnail() {
        Map<String, Object> thumb = thumbnailService.generateThumbnail("doc-1", "PDF");
        assertNotNull(thumb);
        assertEquals("doc-1", thumb.get("documentId"));
        assertFalse((boolean) thumb.get("cached"));

        // Second call should hit cache
        Map<String, Object> cachedThumb = thumbnailService.generateThumbnail("doc-1", "PDF");
        assertTrue((boolean) cachedThumb.get("cached"));
    }

    @Test
    void testClearCache() {
        thumbnailService.generateThumbnail("doc-2", "PNG");
        thumbnailService.clearCache();
        Map<String, Object> stats = thumbnailService.getCacheStatistics();
        assertEquals(0, stats.get("cachedItemsCount"));
    }
}
