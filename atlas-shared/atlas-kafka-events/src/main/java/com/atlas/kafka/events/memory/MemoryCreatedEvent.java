package com.atlas.kafka.events.memory;

import java.time.Instant;

public record MemoryCreatedEvent(
    String eventId,
    String memoryId,
    String tenantId,
    String agentId,
    String key,
    String type,
    double importanceScore,
    Instant timestamp
) {}
