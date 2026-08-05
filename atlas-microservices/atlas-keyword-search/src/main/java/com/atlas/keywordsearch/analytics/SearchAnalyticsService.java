package com.atlas.keywordsearch.analytics;

import com.atlas.domain.analytics.ClickEvent;
import com.atlas.domain.analytics.SearchEvent;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class SearchAnalyticsService {

    private final Queue<SearchEvent> searchEvents = new ConcurrentLinkedQueue<>();
    private final Queue<ClickEvent> clickEvents = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void initSeedAnalytics() {
        log.info("Initializing Search Analytics Service with seed query metrics...");
        recordSearch(SearchEvent.builder()
                .id(UUID.randomUUID().toString())
                .query("atlas search engine")
                .timestamp(Instant.now())
                .latencyMs(18)
                .userId("user-1")
                .retrievalMode("HYBRID")
                .resultCount(25)
                .clickedDocId("doc-foundation-001")
                .clickedPosition(1)
                .sessionId("sess-100")
                .build());

        recordClick(ClickEvent.builder()
                .id(UUID.randomUUID().toString())
                .query("atlas search engine")
                .docId("doc-foundation-001")
                .clickPosition(1)
                .dwellTimeMs(45000)
                .timestamp(Instant.now())
                .sessionId("sess-100")
                .build());
    }

    public void recordSearch(SearchEvent event) {
        if (event == null) return;
        searchEvents.add(event);
        log.info("[SearchAnalytics] Logged Search Event for query '{}' (Latency: {}ms)", event.getQuery(), event.getLatencyMs());
    }

    public void recordClick(ClickEvent event) {
        if (event == null) return;
        clickEvents.add(event);
        log.info("[SearchAnalytics] Logged Click Event for doc '{}' at position {}", event.getDocId(), event.getClickPosition());
    }

    public List<SearchEvent> getRecentSearches() {
        return new ArrayList<>(searchEvents);
    }

    public List<ClickEvent> getRecentClicks() {
        return new ArrayList<>(clickEvents);
    }

    public List<String> getTopQueries() {
        return List.of("atlas search engine", "apache kafka streaming", "spring boot microservices", "bm25 ranking");
    }
}
