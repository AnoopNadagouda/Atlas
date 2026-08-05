package com.atlas.agent.service;

import com.atlas.agent.domain.CoreAgent;
import com.atlas.agent.domain.CoreAgentImpl;
import com.atlas.domain.agent.AgentExecutionConfig;
import com.atlas.domain.agent.AgentLifecycleListener;
import com.atlas.domain.agent.AgentResult;
import com.atlas.domain.agent.AgentState;
import com.atlas.domain.agent.exception.AgentExecutionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AgentLifecycleManagerImpl implements AgentLifecycleManager {

    private final AgentRegistry agentRegistry;
    private final AgentLifecycleListener lifecycleListener;

    @Override
    public CoreAgent createAgent(String agentId, AgentExecutionConfig config) {
        log.info("Creating agent instance with ID: {}", agentId);
        CoreAgent agent = new CoreAgentImpl(agentId, lifecycleListener);
        agent.initialize(config);
        agentRegistry.registerAgent(agent);
        return agent;
    }

    @Override
    public AgentResult executeAgent(String agentId) {
        CoreAgent agent = agentRegistry.getAgent(agentId)
                .orElseThrow(() -> new AgentExecutionException("Agent not found with ID: " + agentId, agentId, null));
        log.info("Starting execution for agent: {}", agentId);
        return agent.execute();
    }

    @Override
    public AgentState getAgentState(String agentId) {
        return agentRegistry.getAgent(agentId)
                .map(CoreAgent::getState)
                .orElse(AgentState.FAILED);
    }
}
