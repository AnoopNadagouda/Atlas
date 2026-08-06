package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.ReasoningAnalyticsResponse;

public interface ReasoningAnalyticsService {
    ReasoningAnalyticsResponse getAnalytics(String tenantId);
}
