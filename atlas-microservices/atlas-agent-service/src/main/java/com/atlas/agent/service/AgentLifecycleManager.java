package com.atlas.agent.service;

import com.atlas.agent.domain.CoreAgent;
import com.atlas.domain.agent.AgentExecutionConfig;
import com.atlas.domain.agent.AgentResult;
import com.atlas.domain.agent.AgentState;

public interface AgentLifecycleManager {
    CoreAgent createAgent(String agentId, AgentExecutionConfig config);
    AgentResult executeAgent(String agentId);
    AgentState getAgentState(String agentId);
}
