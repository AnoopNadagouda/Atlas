package com.atlas.kafka.events.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowStartedEvent {
    private String eventId;
    private String instanceId;
    private String definitionId;
    private Map<String, Object> inputValues;
    private Instant timestamp;
}
