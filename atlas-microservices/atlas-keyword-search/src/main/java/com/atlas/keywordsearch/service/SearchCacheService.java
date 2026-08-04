package com.atlas.keywordsearch.service;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.common.utils.HashUtils;
import com.atlas.common.utils.JsonUtils;
import com.atlas.keywordsearch.config.SearchProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchCacheService {

    private final StringRedisTemplate redisTemplate;
    private final SearchProperties properties;

    private final AtomicLong totalQueries = new AtomicLong(0);
    private final AtomicLong cacheHits = new AtomicLong(0);
    private final AtomicLong cacheMisses = new AtomicLong(0);

    public PageResponse<SearchResultDto> getCachedResponse(String queryText, int page, int size) {
        totalQueries.incrementAndGet();
        String key = buildCacheKey(queryText, page, size);
        try {
            String cachedJson = redisTemplate.opsForValue().get(key);
            if (cachedJson != null) {
                cacheHits.incrementAndGet();
                log.info("Search cache HIT for key: {}", key);
                return JsonUtils.fromJson(cachedJson, PageResponse.class);
            }
        } catch (Exception e) {
            log.warn("Redis read error for key {}: {}", key, e.getMessage());
        }
        cacheMisses.incrementAndGet();
        return null;
    }

    public void cacheResponse(String queryText, int page, int size, PageResponse<SearchResultDto> response) {
        String key = buildCacheKey(queryText, page, size);
        try {
            String json = JsonUtils.toJson(response);
            redisTemplate.opsForValue().set(key, json, Duration.ofSeconds(properties.getCacheTtlSeconds()));
            log.info("Cached search response under key: {}", key);
        } catch (Exception e) {
            log.warn("Redis write error for key {}: {}", key, e.getMessage());
        }
    }

    public void clearCache() {
        try {
            var keys = redisTemplate.keys("search:cache:*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("Cleared {} cached search entries from Redis", keys.size());
            }
        } catch (Exception e) {
            log.error("Failed to clear Redis search cache: {}", e.getMessage());
        }
    }

    public Map<String, Object> getCacheStats() {
        long hits = cacheHits.get();
        long misses = cacheMisses.get();
        long total = hits + misses;
        double ratio = total > 0 ? (double) hits / total : 0.0;

        return Map.of(
                "totalQueries", totalQueries.get(),
                "cacheHits", hits,
                "cacheMisses", misses,
                "cacheHitRatio", ratio,
                "ttlSeconds", properties.getCacheTtlSeconds()
        );
    }

    private String buildCacheKey(String queryText, int page, int size) {
        String hash = HashUtils.sha256(queryText.toLowerCase().trim() + ":" + page + ":" + size);
        return "search:cache:" + hash;
    }
}
