package com.atlas.common.dto.orchestrator;

import java.util.HashMap;
import java.util.Map;

public class PlatformHealthResponse {
    private String overallStatus; // UP, DEGRADED, DOWN
    private Map<String, String> microserviceStatuses = new HashMap<>();

    public PlatformHealthResponse() {
    }

    public String getOverallStatus() { return overallStatus; }
    public void setOverallStatus(String overallStatus) { this.overallStatus = overallStatus; }

    public Map<String, String> getMicroserviceStatuses() { return microserviceStatuses; }
    public void setMicroserviceStatuses(Map<String, String> microserviceStatuses) { this.microserviceStatuses = microserviceStatuses; }
}
