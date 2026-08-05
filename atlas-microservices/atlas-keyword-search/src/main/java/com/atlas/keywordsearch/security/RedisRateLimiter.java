package com.atlas.keywordsearch.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Component
public class RedisRateLimiter {

    private final Map<String, AtomicInteger> requestCounts = new ConcurrentHashMap<>();
    private static final int MAX_REQUESTS_PER_MINUTE = 1000;

    public boolean isAllowed(String key) {
        AtomicInteger count = requestCounts.computeIfAbsent(key, k -> new AtomicInteger(0));
        if (count.incrementAndGet() > MAX_REQUESTS_PER_MINUTE) {
            log.warn("[RedisRateLimiter] Rate limit exceeded for key '{}'", key);
            return false;
        }
        return true;
    }

    public int getRemainingQuota(String key) {
        AtomicInteger count = requestCounts.get(key);
        int used = count != null ? count.get() : 0;
        return Math.max(0, MAX_REQUESTS_PER_MINUTE - used);
    }
}
