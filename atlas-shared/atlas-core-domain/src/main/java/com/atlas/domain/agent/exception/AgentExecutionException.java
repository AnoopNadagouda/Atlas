package com.atlas.domain.agent.exception;

/**
 * Exception thrown when agent execution encounters an unrecoverable failure.
 */
public class AgentExecutionException extends RuntimeException {
    private final String agentId;
    private final String sessionId;

    public AgentExecutionException(String message, String agentId, String sessionId) {
        super(message);
        this.agentId = agentId;
        this.sessionId = sessionId;
    }

    public AgentExecutionException(String message, Throwable cause, String agentId, String sessionId) {
        super(message, cause);
        this.agentId = agentId;
        this.sessionId = sessionId;
    }

    public String getAgentId() {
        return agentId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
