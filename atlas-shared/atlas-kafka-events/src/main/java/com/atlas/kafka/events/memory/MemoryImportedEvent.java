package com.atlas.kafka.events.memory;

import java.time.Instant;

public record MemoryImportedEvent(
    String eventId,
    String tenantId,
    int count,
    Instant timestamp
) {}
