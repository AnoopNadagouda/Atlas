package com.atlas.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

/**
 * Result record produced upon agent completion or failure.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentResult {
    private String agentId;
    private String sessionId;
    private AgentState finalState;
    private String output;
    private int totalIterations;
    private long executionTimeMs;
    private List<String> toolsInvoked;
    private Map<String, Object> metrics;
    private String errorMessage;
    @Builder.Default
    private Instant completedAt = Instant.now();

    public boolean isSuccess() {
        return finalState == AgentState.COMPLETED;
    }
}
