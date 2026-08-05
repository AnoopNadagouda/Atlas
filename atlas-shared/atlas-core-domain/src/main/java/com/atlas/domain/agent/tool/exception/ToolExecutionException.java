package com.atlas.domain.agent.tool.exception;

/**
 * Exception thrown when a tool execution fails during runtime.
 */
public class ToolExecutionException extends RuntimeException {
    private final String toolId;

    public ToolExecutionException(String toolId, String message) {
        super(message);
        this.toolId = toolId;
    }

    public ToolExecutionException(String toolId, String message, Throwable cause) {
        super(message, cause);
        this.toolId = toolId;
    }

    public String getToolId() {
        return toolId;
    }
}
