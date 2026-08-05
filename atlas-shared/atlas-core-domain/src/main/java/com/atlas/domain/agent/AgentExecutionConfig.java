package com.atlas.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Execution parameters and constraints for an autonomous agent run.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentExecutionConfig {
    private String agentId;
    private String sessionId;
    private String tenantId;
    private String userPrompt;
    @Builder.Default
    private int maxIterations = 25;
    @Builder.Default
    private long timeoutMs = 60000L;
    @Builder.Default
    private boolean enableSelfCorrection = true;
    @Builder.Default
    private boolean enableMemory = true;
    @Builder.Default
    private boolean enableStreaming = true;
    private List<String> allowedTools;
    private Map<String, Object> metadata;
}
