package com.atlas.orchestrator.service;

import com.atlas.common.dto.orchestrator.PlatformHealthResponse;
import com.atlas.common.dto.orchestrator.PlatformStatisticsResponse;

public interface PlatformHealthManager {
    PlatformHealthResponse getHealth();
    PlatformStatisticsResponse getStatistics();
}
