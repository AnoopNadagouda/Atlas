package com.atlas.kafka.events.memory;

import java.time.Instant;

public record MemoryRetrievedEvent(
    String eventId,
    String memoryId,
    String tenantId,
    String query,
    double relevanceScore,
    Instant timestamp
) {}
