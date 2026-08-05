package com.atlas.domain.agent.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Metadata descriptor exposed by every Agent Tool for discovery and schema validation.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolMetadata {
    private String id;
    private String displayName;
    private String description;
    @Builder.Default
    private String version = "1.0.0";
    private ToolCategory category;
    private Set<ToolPermission> permissions;
    private List<ToolParameter> parameters;
    private Map<String, Object> inputSchema;
    private Map<String, Object> outputSchema;
    @Builder.Default
    private long timeoutMs = 30000L;
    @Builder.Default
    private boolean supportsStreaming = false;
    @Builder.Default
    private boolean requiresAuthentication = false;
    @Builder.Default
    private boolean supportsCancellation = true;
}
