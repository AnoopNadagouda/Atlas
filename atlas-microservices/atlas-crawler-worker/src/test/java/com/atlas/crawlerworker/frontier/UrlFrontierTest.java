package com.atlas.crawlerworker.frontier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UrlFrontierTest {

    private UrlFrontier frontier;

    @BeforeEach
    void setUp() {
        frontier = new UrlFrontier();
    }

    @Test
    void testFrontierSchedulingAndDeduplication() {
        UrlFrontier.CrawlTask task1 = UrlFrontier.CrawlTask.builder()
                .jobId("job-1")
                .url("https://example.com/docs")
                .normalizedUrl("https://example.com/docs")
                .depth(0)
                .priority(5)
                .build();

        UrlFrontier.CrawlTask task2 = UrlFrontier.CrawlTask.builder()
                .jobId("job-1")
                .url("https://example.com/docs#frag")
                .normalizedUrl("https://example.com/docs")
                .depth(0)
                .priority(5)
                .build();

        assertTrue(frontier.schedule(task1));
        assertFalse(frontier.schedule(task2)); // Duplicate normalized URL rejected
        assertEquals(1, frontier.queueSize());
    }
}
