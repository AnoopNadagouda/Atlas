package com.atlas.domain.agent;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AgentStateTest {

    @Test
    @DisplayName("Should validate terminal states correctly")
    void testTerminalStates() {
        assertTrue(AgentState.COMPLETED.isTerminal());
        assertTrue(AgentState.FAILED.isTerminal());
        assertFalse(AgentState.RUNNING.isTerminal());
        assertFalse(AgentState.READY.isTerminal());
    }

    @Test
    @DisplayName("Should validate valid state transitions")
    void testValidTransitions() {
        assertTrue(AgentState.CREATED.canTransitionTo(AgentState.INITIALIZING));
        assertTrue(AgentState.INITIALIZING.canTransitionTo(AgentState.READY));
        assertTrue(AgentState.READY.canTransitionTo(AgentState.RUNNING));
        assertTrue(AgentState.RUNNING.canTransitionTo(AgentState.WAITING_FOR_TOOL));
        assertTrue(AgentState.WAITING_FOR_TOOL.canTransitionTo(AgentState.RUNNING));
        assertTrue(AgentState.RUNNING.canTransitionTo(AgentState.SELF_CORRECTING));
        assertTrue(AgentState.SELF_CORRECTING.canTransitionTo(AgentState.RUNNING));
        assertTrue(AgentState.RUNNING.canTransitionTo(AgentState.COMPLETED));
    }

    @Test
    @DisplayName("Should prevent invalid state transitions from terminal state")
    void testTerminalStateLock() {
        assertFalse(AgentState.COMPLETED.canTransitionTo(AgentState.RUNNING));
        assertFalse(AgentState.FAILED.canTransitionTo(AgentState.READY));
    }
}
