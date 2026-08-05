package com.atlas.agent.tool.registry;

import com.atlas.domain.agent.tool.AgentTool;
import com.atlas.domain.agent.tool.ToolCategory;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

public interface ToolRegistry {
    void registerTool(AgentTool tool);
    Optional<AgentTool> getTool(String id);
    Collection<AgentTool> getAllTools();
    Collection<AgentTool> getToolsByCategory(ToolCategory category);
    boolean isToolEnabled(String id);
    void setToolEnabled(String id, boolean enabled);
    Map<String, Boolean> checkHealth();
    int getToolCount();
}
