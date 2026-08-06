package com.atlas.reasoning.integration;

import com.atlas.reasoning.repository.ReasoningSessionRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class ReasoningHealthIndicator implements HealthIndicator {

    private final ReasoningSessionRepository sessionRepository;

    public ReasoningHealthIndicator(ReasoningSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Health health() {
        try {
            long totalSessions = sessionRepository.count();
            return Health.up()
                    .withDetail("service", "atlas-reasoning-service")
                    .withDetail("status", "UP")
                    .withDetail("totalSessions", totalSessions)
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("service", "atlas-reasoning-service")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
