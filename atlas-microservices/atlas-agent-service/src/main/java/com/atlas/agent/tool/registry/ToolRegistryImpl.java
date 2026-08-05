package com.atlas.agent.tool.registry;

import com.atlas.domain.agent.tool.AgentTool;
import com.atlas.domain.agent.tool.ToolCategory;
import com.atlas.domain.agent.tool.ToolMetadata;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ToolRegistryImpl implements ToolRegistry {

    private final Map<String, AgentTool> tools = new ConcurrentHashMap<>();
    private final Map<String, Boolean> enabledStates = new ConcurrentHashMap<>();

    // Constructor injection of all Spring-managed AgentTool implementations
    public ToolRegistryImpl(List<AgentTool> springTools) {
        if (springTools != null) {
            for (AgentTool tool : springTools) {
                registerTool(tool);
            }
        }
    }

    @Override
    public synchronized void registerTool(AgentTool tool) {
        if (tool == null || tool.getMetadata() == null || tool.getMetadata().getId() == null) {
            throw new IllegalArgumentException("Cannot register tool with null metadata or ID");
        }
        String id = tool.getMetadata().getId();
        if (tools.containsKey(id)) {
            log.warn("Overwriting duplicate registration for tool ID: {}", id);
        }
        tools.put(id, tool);
        enabledStates.putIfAbsent(id, true);
        log.info("Registered Agent Tool: [{}] - Category: {}", id, tool.getMetadata().getCategory());
    }

    @Override
    public Optional<AgentTool> getTool(String id) {
        if (id == null) return Optional.empty();
        return Optional.ofNullable(tools.get(id));
    }

    @Override
    public Collection<AgentTool> getAllTools() {
        return Collections.unmodifiableCollection(tools.values());
    }

    @Override
    public Collection<AgentTool> getToolsByCategory(ToolCategory category) {
        if (category == null) return Collections.emptyList();
        return tools.values().stream()
                .filter(t -> t.getMetadata() != null && category.equals(t.getMetadata().getCategory()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isToolEnabled(String id) {
        return enabledStates.getOrDefault(id, false);
    }

    @Override
    public void setToolEnabled(String id, boolean enabled) {
        if (tools.containsKey(id)) {
            enabledStates.put(id, enabled);
            log.info("Set tool [{}] enabled status to: {}", id, enabled);
        }
    }

    @Override
    public Map<String, Boolean> checkHealth() {
        Map<String, Boolean> healthMap = new HashMap<>();
        tools.forEach((id, tool) -> {
            try {
                boolean healthy = isToolEnabled(id) && tool.isHealthy();
                healthMap.put(id, healthy);
            } catch (Exception e) {
                log.error("Health check failed for tool: {}", id, e);
                healthMap.put(id, false);
            }
        });
        return healthMap;
    }

    @Override
    public int getToolCount() {
        return tools.size();
    }
}
