package com.atlas.keywordsearch.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RedisRateLimiterTest {

    private RedisRateLimiter rateLimiter;

    @BeforeEach
    void setUp() {
        rateLimiter = new RedisRateLimiter();
    }

    @Test
    void testRateLimiterQuota() {
        assertTrue(rateLimiter.isAllowed("user-123"));
        assertTrue(rateLimiter.getRemainingQuota("user-123") < 1000);
    }
}
