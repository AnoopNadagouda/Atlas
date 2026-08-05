package com.atlas.domain.agent.tool;

import java.util.Map;

/**
 * Interface implemented by every executable tool in the Atlas Agent ecosystem.
 */
public interface AgentTool {
    ToolMetadata getMetadata();
    Map<String, Object> execute(Map<String, Object> parameters) throws Exception;
    
    default boolean isHealthy() {
        return true;
    }
}
