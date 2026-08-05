package com.atlas.domain.agent.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Immutable parameter specification for tool input arguments.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolParameter {
    private String name;
    private String type; // e.g. "string", "integer", "boolean", "object", "array"
    private String description;
    private boolean required;
    private Object defaultValue;
}
