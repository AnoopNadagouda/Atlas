package com.atlas.domain.agent.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.Set;

/**
 * Request container passed to ToolExecutor for executing an Agent Tool.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolExecutionRequest {
    private String toolId;
    private String executionId;
    private String agentId;
    private String sessionId;
    private Map<String, Object> parameters;
    private Set<ToolPermission> callerPermissions;
    private long timeoutMs;
}
