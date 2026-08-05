package com.atlas.domain.agent.tool.exception;

import java.util.List;

/**
 * Exception thrown when input parameters fail JSON schema validation.
 */
public class ToolValidationException extends RuntimeException {
    private final String toolId;
    private final List<String> validationErrors;

    public ToolValidationException(String toolId, String message, List<String> validationErrors) {
        super(message);
        this.toolId = toolId;
        this.validationErrors = validationErrors;
    }

    public String getToolId() {
        return toolId;
    }

    public List<String> getValidationErrors() {
        return validationErrors;
    }
}
