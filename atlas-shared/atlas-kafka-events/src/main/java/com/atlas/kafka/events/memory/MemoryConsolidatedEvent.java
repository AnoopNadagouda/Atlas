package com.atlas.kafka.events.memory;

import java.time.Instant;
import java.util.List;

public record MemoryConsolidatedEvent(
    String eventId,
    String consolidatedMemoryId,
    String tenantId,
    List<String> sourceMemoryIds,
    Instant timestamp
) {}
