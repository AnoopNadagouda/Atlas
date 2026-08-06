package com.atlas.memory.integration;

import com.atlas.memory.repository.MemoryRepository;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class MemoryHealthIndicator implements HealthIndicator {

    private final MemoryRepository memoryRepository;

    public MemoryHealthIndicator(MemoryRepository memoryRepository) {
        this.memoryRepository = memoryRepository;
    }

    @Override
    public Health health() {
        try {
            long totalMemories = memoryRepository.count();
            return Health.up()
                    .withDetail("service", "atlas-memory-service")
                    .withDetail("status", "UP")
                    .withDetail("totalMemories", totalMemories)
                    .build();
        } catch (Exception e) {
            return Health.down(e)
                    .withDetail("service", "atlas-memory-service")
                    .withDetail("error", e.getMessage())
                    .build();
        }
    }
}
