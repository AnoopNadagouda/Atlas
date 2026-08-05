package com.atlas.domain.agent.exception;

/**
 * Exception thrown when initializing an agent or validating its configuration fails.
 */
public class AgentInitializationException extends RuntimeException {
    public AgentInitializationException(String message) {
        super(message);
    }

    public AgentInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
