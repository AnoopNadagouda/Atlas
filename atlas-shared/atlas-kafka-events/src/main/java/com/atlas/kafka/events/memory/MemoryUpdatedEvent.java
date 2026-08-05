package com.atlas.kafka.events.memory;

import java.time.Instant;

public record MemoryUpdatedEvent(
    String eventId,
    String memoryId,
    String tenantId,
    String state,
    double importanceScore,
    Instant timestamp
) {}
