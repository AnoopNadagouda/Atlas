package com.atlas.agent.service;

import com.atlas.agent.domain.CoreAgent;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AgentRegistryImpl implements AgentRegistry {

    private final Map<String, CoreAgent> registry = new ConcurrentHashMap<>();

    @Override
    public void registerAgent(CoreAgent agent) {
        if (agent != null && agent.getAgentId() != null) {
            registry.put(agent.getAgentId(), agent);
        }
    }

    @Override
    public Optional<CoreAgent> getAgent(String agentId) {
        return Optional.ofNullable(registry.get(agentId));
    }

    @Override
    public Collection<CoreAgent> getAllAgents() {
        return registry.values();
    }

    @Override
    public boolean unregisterAgent(String agentId) {
        return registry.remove(agentId) != null;
    }
}
