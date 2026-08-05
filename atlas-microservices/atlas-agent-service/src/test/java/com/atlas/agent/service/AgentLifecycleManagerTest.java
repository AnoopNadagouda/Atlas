package com.atlas.agent.service;

import com.atlas.agent.config.AgentCoreAutoConfiguration;
import com.atlas.agent.domain.CoreAgent;
import com.atlas.domain.agent.AgentExecutionConfig;
import com.atlas.domain.agent.AgentResult;
import com.atlas.domain.agent.AgentState;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentLifecycleManagerTest {

    private AgentRegistry registry;
    private AgentLifecycleManager lifecycleManager;

    @BeforeEach
    void setUp() {
        registry = new AgentRegistryImpl();
        lifecycleManager = new AgentLifecycleManagerImpl(registry, new AgentCoreAutoConfiguration().defaultAgentLifecycleListener());
    }

    @Test
    @DisplayName("Should create and register agent in READY state")
    void testAgentCreation() {
        AgentExecutionConfig config = AgentExecutionConfig.builder()
                .agentId("agent-001")
                .sessionId("sess-001")
                .userPrompt("Perform test search")
                .build();

        CoreAgent agent = lifecycleManager.createAgent("agent-001", config);

        assertNotNull(agent);
        assertEquals("agent-001", agent.getAgentId());
        assertEquals(AgentState.READY, agent.getState());
        assertTrue(registry.getAgent("agent-001").isPresent());
    }

    @Test
    @DisplayName("Should execute registered agent and transition to COMPLETED")
    void testAgentExecution() {
        AgentExecutionConfig config = AgentExecutionConfig.builder()
                .agentId("agent-002")
                .sessionId("sess-002")
                .userPrompt("Run analysis")
                .build();

        lifecycleManager.createAgent("agent-002", config);
        AgentResult result = lifecycleManager.executeAgent("agent-002");

        assertNotNull(result);
        assertEquals(AgentState.COMPLETED, result.getFinalState());
        assertTrue(result.isSuccess());
        assertEquals(AgentState.COMPLETED, lifecycleManager.getAgentState("agent-002"));
    }
}
