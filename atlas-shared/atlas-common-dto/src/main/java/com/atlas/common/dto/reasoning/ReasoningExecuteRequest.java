package com.atlas.common.dto.reasoning;

import com.atlas.domain.reasoning.ReasoningMode;

import java.util.HashMap;
import java.util.Map;

public class ReasoningExecuteRequest {
    private String goalId;
    private String agentId;
    private ReasoningMode mode = ReasoningMode.CHAIN_OF_THOUGHT;
    private Map<String, Object> contextParams = new HashMap<>();

    public ReasoningExecuteRequest() {
    }

    public String getGoalId() { return goalId; }
    public void setGoalId(String goalId) { this.goalId = goalId; }

    public String getAgentId() { return agentId; }
    public void setAgentId(String agentId) { this.agentId = agentId; }

    public ReasoningMode getMode() { return mode; }
    public void setMode(ReasoningMode mode) { this.mode = mode; }

    public Map<String, Object> getContextParams() { return contextParams; }
    public void setContextParams(Map<String, Object> contextParams) { this.contextParams = contextParams; }
}
