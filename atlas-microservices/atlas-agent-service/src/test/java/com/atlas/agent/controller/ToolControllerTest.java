package com.atlas.agent.controller;

import com.atlas.agent.tool.executor.ToolExecutor;
import com.atlas.agent.tool.registry.ToolRegistry;
import com.atlas.domain.agent.tool.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ToolControllerTest {

    private ToolRegistry registry;
    private ToolExecutor executor;
    private ToolController controller;

    @BeforeEach
    void setUp() {
        registry = mock(ToolRegistry.class);
        executor = mock(ToolExecutor.class);
        controller = new ToolController(registry, executor);
    }

    @Test
    @DisplayName("Should return list of registered tools")
    void testListTools() {
        AgentTool mockTool = mock(AgentTool.class);
        when(mockTool.getMetadata()).thenReturn(ToolMetadata.builder().id("t1").category(ToolCategory.SEARCH).build());
        when(registry.getAllTools()).thenReturn(List.of(mockTool));

        var response = controller.listTools(null);
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("t1", response.getBody().getFirst().getId());
    }

    @Test
    @DisplayName("Should return tool by ID")
    void testGetToolById() {
        AgentTool mockTool = mock(AgentTool.class);
        when(mockTool.getMetadata()).thenReturn(ToolMetadata.builder().id("t1").build());
        when(registry.getTool("t1")).thenReturn(Optional.of(mockTool));

        var response = controller.getToolById("t1");
        assertNotNull(response.getBody());
        assertEquals("t1", response.getBody().getId());
    }

    @Test
    @DisplayName("Should execute tool via ToolExecutor")
    void testExecuteTool() {
        ToolExecutionResult result = ToolExecutionResult.builder()
                .toolId("t1")
                .status(ToolExecutionStatus.SUCCESS)
                .output(Map.of("res", "ok"))
                .build();
        when(executor.executeTool(any())).thenReturn(result);

        ToolExecutionRequest req = ToolExecutionRequest.builder().toolId("t1").build();
        var response = controller.executeTool(req);

        assertNotNull(response.getBody());
        assertEquals(ToolExecutionStatus.SUCCESS, response.getBody().getStatus());
    }
}
