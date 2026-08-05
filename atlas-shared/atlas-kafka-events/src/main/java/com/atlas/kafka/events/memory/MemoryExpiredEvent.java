package com.atlas.kafka.events.memory;

import java.time.Instant;

public record MemoryExpiredEvent(
    String eventId,
    String memoryId,
    String tenantId,
    Instant timestamp
) {}
