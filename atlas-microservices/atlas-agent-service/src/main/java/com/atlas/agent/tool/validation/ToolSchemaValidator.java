package com.atlas.agent.tool.validation;

import com.atlas.domain.agent.tool.ToolMetadata;
import com.atlas.domain.agent.tool.ToolParameter;
import com.atlas.domain.agent.tool.exception.ToolValidationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ToolSchemaValidator {

    public void validate(ToolMetadata metadata, Map<String, Object> parameters) {
        if (metadata == null) {
            throw new ToolValidationException("UNKNOWN", "Metadata cannot be null", List.of("Tool metadata missing"));
        }
        String toolId = metadata.getId();
        List<String> errors = new ArrayList<>();

        if (parameters == null) {
            parameters = Map.of();
        }

        if (metadata.getParameters() != null) {
            for (ToolParameter param : metadata.getParameters()) {
                Object value = parameters.get(param.getName());
                
                // Check required fields
                if (param.isRequired() && (value == null || (value instanceof String s && s.trim().isEmpty()))) {
                    errors.add("Required parameter '" + param.getName() + "' is missing or empty.");
                    continue;
                }

                // Type checking if value is provided
                if (value != null && param.getType() != null) {
                    validateType(param.getName(), param.getType(), value, errors);
                }
            }
        }

        if (!errors.isEmpty()) {
            throw new ToolValidationException(toolId, "Validation failed for tool execution: " + toolId, errors);
        }
    }

    private void validateType(String paramName, String expectedType, Object value, List<String> errors) {
        switch (expectedType.toLowerCase()) {
            case "string" -> {
                if (!(value instanceof String)) {
                    errors.add("Parameter '" + paramName + "' expected type 'string' but got " + value.getClass().getSimpleName());
                }
            }
            case "integer", "number", "int" -> {
                if (!(value instanceof Number)) {
                    errors.add("Parameter '" + paramName + "' expected type 'number' but got " + value.getClass().getSimpleName());
                }
            }
            case "boolean" -> {
                if (!(value instanceof Boolean)) {
                    errors.add("Parameter '" + paramName + "' expected type 'boolean' but got " + value.getClass().getSimpleName());
                }
            }
            case "object" -> {
                if (!(value instanceof Map)) {
                    errors.add("Parameter '" + paramName + "' expected type 'object' but got " + value.getClass().getSimpleName());
                }
            }
            case "array", "list" -> {
                if (!(value instanceof List)) {
                    errors.add("Parameter '" + paramName + "' expected type 'array' but got " + value.getClass().getSimpleName());
                }
            }
        }
    }
}
