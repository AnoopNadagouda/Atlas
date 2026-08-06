package com.atlas.orchestrator.integration;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class OrchestratorHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        return Health.up()
            .withDetail("service", "atlas-orchestrator-service")
            .withDetail("status", "AIOS Autonomous Orchestrator Running")
            .withDetail("port", 8090)
            .build();
    }
}
