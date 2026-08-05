package com.atlas.agent.domain;

import com.atlas.domain.agent.AgentContext;
import com.atlas.domain.agent.AgentExecutionConfig;
import com.atlas.domain.agent.AgentResult;
import com.atlas.domain.agent.AgentState;

public interface CoreAgent {
    String getAgentId();
    AgentContext getContext();
    AgentState getState();
    void initialize(AgentExecutionConfig config);
    AgentResult execute();
    void pause();
    void resume();
}
