package com.atlas.agent.service;

import com.atlas.agent.domain.CoreAgent;

import java.util.Collection;
import java.util.Optional;

public interface AgentRegistry {
    void registerAgent(CoreAgent agent);
    Optional<CoreAgent> getAgent(String agentId);
    Collection<CoreAgent> getAllAgents();
    boolean unregisterAgent(String agentId);
}
