package com.atlas.common.dto.agent;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AgentCreateRequest {
    private String name;
    private String description;
    private String systemPrompt;
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
