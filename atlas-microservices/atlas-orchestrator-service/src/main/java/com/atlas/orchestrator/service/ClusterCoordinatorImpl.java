package com.atlas.orchestrator.service;

import com.atlas.common.dto.orchestrator.ClusterOverviewResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClusterCoordinatorImpl implements ClusterCoordinator {

    private static final Logger log = LoggerFactory.getLogger(ClusterCoordinatorImpl.class);

    @Override
    public ClusterOverviewResponse getClusterOverview() {
        log.info("[ClusterCoordinator] Generating AIOS distributed cluster topology overview");
        ClusterOverviewResponse response = new ClusterOverviewResponse();
        response.setClusterId("atlas-cluster-primary");
        response.setTotalNodes(12);
        response.setHealthyServicesCount(10);
        response.setCpuUtilization(24.5);
        response.setMemoryUtilization(48.2);
        response.setNodeNames(List.of(
            "atlas-node-us-east-1a",
            "atlas-node-us-east-1b",
            "atlas-node-us-west-2a",
            "atlas-node-eu-central-1a"
        ));
        return response;
    }
}
