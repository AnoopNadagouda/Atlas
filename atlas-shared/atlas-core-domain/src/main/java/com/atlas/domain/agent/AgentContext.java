package com.atlas.domain.agent;

import lombok.Getter;
import lombok.ToString;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Thread-safe execution context for an active AI Agent session.
 */
@Getter
@ToString
public class AgentContext {
    private final String sessionId;
    private final String agentId;
    private final AgentExecutionConfig config;
    private final Instant createdAt;
    private final Map<String, Object> variables = new ConcurrentHashMap<>();
    private final List<String> executionLogs = Collections.synchronizedList(new ArrayList<>());
    private final List<String> toolsInvoked = Collections.synchronizedList(new ArrayList<>());
    
    private volatile AgentState state;
    private volatile int currentIteration;
    private volatile String lastError;

    public AgentContext(String sessionId, String agentId, AgentExecutionConfig config) {
        this.sessionId = sessionId;
        this.agentId = agentId;
        this.config = config != null ? config : AgentExecutionConfig.builder().agentId(agentId).sessionId(sessionId).build();
        this.createdAt = Instant.now();
        this.state = AgentState.CREATED;
        this.currentIteration = 0;
    }

    public synchronized boolean transitionState(AgentState newState) {
        if (this.state.canTransitionTo(newState)) {
            this.state = newState;
            return true;
        }
        return false;
    }

    public void incrementIteration() {
        this.currentIteration++;
    }

    public void log(String message) {
        this.executionLogs.add("[" + Instant.now() + "] " + message);
    }

    public void recordToolInvocation(String toolName) {
        this.toolsInvoked.add(toolName);
    }

    public void setVariable(String key, Object value) {
        if (value != null) {
            this.variables.put(key, value);
        } else {
            this.variables.remove(key);
        }
    }

    public Object getVariable(String key) {
        return this.variables.get(key);
    }

    public void setLastError(String error) {
        this.lastError = error;
    }
}
