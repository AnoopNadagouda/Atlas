package com.atlas.kafka.events.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StepFailedEvent {
    private String eventId;
    private String instanceId;
    private String stepId;
    private String errorMessage;
    private int retryCount;
    private Instant timestamp;
}
