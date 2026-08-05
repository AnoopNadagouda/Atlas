package com.atlas.common.dto.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentExecutionResponse {
    private String agentId;
    private String sessionId;
    private String status;
    private String output;
    private int totalIterations;
    private long executionTimeMs;
    private List<String> toolsInvoked;
    private Map<String, Object> metrics;
    private String errorMessage;
    private Instant completedAt;
}
