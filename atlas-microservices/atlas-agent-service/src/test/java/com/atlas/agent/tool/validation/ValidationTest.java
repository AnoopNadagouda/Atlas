package com.atlas.agent.tool.validation;

import com.atlas.domain.agent.tool.ToolMetadata;
import com.atlas.domain.agent.tool.ToolParameter;
import com.atlas.domain.agent.tool.exception.ToolValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ValidationTest {

    private ToolSchemaValidator validator;
    private ToolMetadata metadata;

    @BeforeEach
    void setUp() {
        validator = new ToolSchemaValidator();
        metadata = ToolMetadata.builder()
                .id("validation_tool")
                .parameters(List.of(
                        ToolParameter.builder().name("q").type("string").required(true).build(),
                        ToolParameter.builder().name("limit").type("integer").required(false).build()
                ))
                .build();
    }

    @Test
    @DisplayName("Should pass validation with valid parameters")
    void testValidParameters() {
        assertDoesNotThrow(() -> validator.validate(metadata, Map.of("q", "atlas search", "limit", 10)));
    }

    @Test
    @DisplayName("Should throw ToolValidationException when required parameter is missing")
    void testMissingRequiredParameter() {
        ToolValidationException ex = assertThrows(ToolValidationException.class, () -> validator.validate(metadata, Map.of("limit", 10)));
        assertEquals("validation_tool", ex.getToolId());
        assertFalse(ex.getValidationErrors().isEmpty());
    }

    @Test
    @DisplayName("Should throw ToolValidationException when parameter type mismatches")
    void testTypeMismatchParameter() {
        ToolValidationException ex = assertThrows(ToolValidationException.class, () -> validator.validate(metadata, Map.of("q", "valid", "limit", "not_a_number")));
        assertFalse(ex.getValidationErrors().isEmpty());
    }
}
