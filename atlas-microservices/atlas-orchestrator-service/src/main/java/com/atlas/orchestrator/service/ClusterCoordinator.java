package com.atlas.orchestrator.service;

import com.atlas.common.dto.orchestrator.ClusterOverviewResponse;

public interface ClusterCoordinator {
    ClusterOverviewResponse getClusterOverview();
}
