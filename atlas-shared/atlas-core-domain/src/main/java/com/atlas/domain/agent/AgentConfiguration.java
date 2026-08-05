package com.atlas.domain.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Configuration settings for an Autonomous AI Agent execution session.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentConfiguration {
    private String agentId;
    private String name;
    private String description;
    private int maxIterations;
    private long timeoutMs;
    private boolean enableSelfCorrection;
    private boolean enableMemory;
    private boolean enableStreaming;
    private List<String> allowedTools;
    private String defaultWorkflow;
}
