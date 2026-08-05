package com.atlas.keywordsearch.analytics;

import com.atlas.domain.analytics.ClickEvent;
import com.atlas.domain.analytics.SearchEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class SearchAnalyticsServiceTest {

    private SearchAnalyticsService analyticsService;

    @BeforeEach
    void setUp() {
        analyticsService = new SearchAnalyticsService();
        analyticsService.initSeedAnalytics();
    }

    @Test
    void testRecordSearchAndClick() {
        analyticsService.recordSearch(SearchEvent.builder()
                .id(UUID.randomUUID().toString())
                .query("test query")
                .timestamp(Instant.now())
                .latencyMs(12)
                .userId("u1")
                .retrievalMode("HYBRID")
                .resultCount(10)
                .sessionId("s1")
                .build());

        analyticsService.recordClick(ClickEvent.builder()
                .id(UUID.randomUUID().toString())
                .query("test query")
                .docId("doc-1")
                .clickPosition(1)
                .dwellTimeMs(5000)
                .timestamp(Instant.now())
                .sessionId("s1")
                .build());

        assertEquals(2, analyticsService.getRecentSearches().size());
        assertEquals(2, analyticsService.getRecentClicks().size());
    }
}
