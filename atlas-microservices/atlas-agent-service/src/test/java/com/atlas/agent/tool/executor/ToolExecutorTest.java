package com.atlas.agent.tool.executor;

import com.atlas.agent.tool.registry.ToolRegistry;
import com.atlas.agent.tool.registry.ToolRegistryImpl;
import com.atlas.agent.tool.validation.ToolSchemaValidator;
import com.atlas.domain.agent.tool.*;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ToolExecutorTest {

    private ToolRegistry registry;
    private ToolExecutor executor;

    @BeforeEach
    void setUp() {
        AgentTool dummyTool = new AgentTool() {
            @Override
            public ToolMetadata getMetadata() {
                return ToolMetadata.builder()
                        .id("dummy_tool")
                        .displayName("Dummy Tool")
                        .category(ToolCategory.SEARCH)
                        .permissions(Set.of(ToolPermission.SEARCH_EXECUTE))
                        .parameters(List.of(ToolParameter.builder().name("key").type("string").required(true).build()))
                        .timeoutMs(5000L)
                        .build();
            }

            @Override
            public Map<String, Object> execute(Map<String, Object> parameters) {
                return Map.of("result", "value_" + parameters.get("key"));
            }
        };

        registry = new ToolRegistryImpl(List.of(dummyTool));
        executor = new ToolExecutorImpl(registry, new ToolSchemaValidator(), new SimpleMeterRegistry());
    }

    @Test
    @DisplayName("Should successfully execute tool when permissions and parameters are valid")
    void testSuccessfulExecution() {
        ToolExecutionRequest request = ToolExecutionRequest.builder()
                .toolId("dummy_tool")
                .executionId("exec-001")
                .callerPermissions(Set.of(ToolPermission.SEARCH_EXECUTE))
                .parameters(Map.of("key", "test"))
                .build();

        ToolExecutionResult result = executor.executeTool(request);

        assertNotNull(result);
        assertEquals(ToolExecutionStatus.SUCCESS, result.getStatus());
        assertTrue(result.isSuccess());
        assertEquals("value_test", result.getOutput().get("result"));
    }

    @Test
    @DisplayName("Should reject execution when caller lacks required permissions")
    void testPermissionDenied() {
        ToolExecutionRequest request = ToolExecutionRequest.builder()
                .toolId("dummy_tool")
                .executionId("exec-002")
                .callerPermissions(Set.of()) // No permissions
                .parameters(Map.of("key", "test"))
                .build();

        ToolExecutionResult result = executor.executeTool(request);

        assertNotNull(result);
        assertEquals(ToolExecutionStatus.PERMISSION_DENIED, result.getStatus());
        assertFalse(result.isSuccess());
    }

    @Test
    @DisplayName("Should reject execution when validation fails")
    void testValidationError() {
        ToolExecutionRequest request = ToolExecutionRequest.builder()
                .toolId("dummy_tool")
                .executionId("exec-003")
                .callerPermissions(Set.of(ToolPermission.SEARCH_EXECUTE))
                .parameters(Map.of()) // Missing required 'key'
                .build();

        ToolExecutionResult result = executor.executeTool(request);

        assertNotNull(result);
        assertEquals(ToolExecutionStatus.VALIDATION_ERROR, result.getStatus());
    }
}
