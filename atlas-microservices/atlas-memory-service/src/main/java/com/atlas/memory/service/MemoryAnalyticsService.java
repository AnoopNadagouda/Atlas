package com.atlas.memory.service;

import com.atlas.domain.memory.MemoryAnalytics;

public interface MemoryAnalyticsService {
    MemoryAnalytics calculateAnalytics(String tenantId);
}
