package com.atlas.domain.agent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentContextTest {

    @Test
    @DisplayName("Should initialize context with CREATED state")
    void testInitialization() {
        AgentExecutionConfig config = AgentExecutionConfig.builder()
                .agentId("agent-1")
                .sessionId("session-123")
                .userPrompt("Test prompt")
                .build();
        AgentContext context = new AgentContext("session-123", "agent-1", config);

        assertEquals("session-123", context.getSessionId());
        assertEquals("agent-1", context.getAgentId());
        assertEquals(AgentState.CREATED, context.getState());
        assertEquals(0, context.getCurrentIteration());
    }

    @Test
    @DisplayName("Should execute valid state transitions and track logs")
    void testStateTransitionsAndLogging() {
        AgentContext context = new AgentContext("session-123", "agent-1", null);

        assertTrue(context.transitionState(AgentState.INITIALIZING));
        assertEquals(AgentState.INITIALIZING, context.getState());

        assertTrue(context.transitionState(AgentState.READY));
        assertEquals(AgentState.READY, context.getState());

        context.log("Agent started execution");
        assertEquals(1, context.getExecutionLogs().size());
        assertTrue(context.getExecutionLogs().get(0).contains("Agent started execution"));

        context.recordToolInvocation("bm25_search");
        assertEquals(1, context.getToolsInvoked().size());
        assertEquals("bm25_search", context.getToolsInvoked().get(0));
    }
}
