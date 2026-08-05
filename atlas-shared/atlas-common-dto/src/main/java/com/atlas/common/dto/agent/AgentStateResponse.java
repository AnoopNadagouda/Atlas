package com.atlas.common.dto.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentStateResponse {
    private String agentId;
    private String sessionId;
    private String state;
    private int currentIteration;
    private int maxIterations;
    private List<String> toolsInvoked;
    private String lastError;
    private Instant createdAt;
}
