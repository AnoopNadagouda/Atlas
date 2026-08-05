package com.atlas.agent.tool.registry;

import com.atlas.domain.agent.tool.AgentTool;
import com.atlas.domain.agent.tool.ToolCategory;
import com.atlas.domain.agent.tool.ToolMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ToolRegistryTest {

    private ToolRegistry registry;
    private AgentTool mockTool;

    @BeforeEach
    void setUp() {
        mockTool = new AgentTool() {
            @Override
            public ToolMetadata getMetadata() {
                return ToolMetadata.builder()
                        .id("test_tool")
                        .displayName("Test Tool")
                        .category(ToolCategory.SEARCH)
                        .build();
            }

            @Override
            public Map<String, Object> execute(Map<String, Object> parameters) {
                return Map.of("status", "ok");
            }
        };
        registry = new ToolRegistryImpl(List.of(mockTool));
    }

    @Test
    @DisplayName("Should auto-register tools injected via constructor")
    void testAutoRegistration() {
        assertEquals(1, registry.getToolCount());
        Optional<AgentTool> tool = registry.getTool("test_tool");
        assertTrue(tool.isPresent());
        assertEquals("test_tool", tool.get().getMetadata().getId());
    }

    @Test
    @DisplayName("Should retrieve tools by category")
    void testGetByCategory() {
        assertEquals(1, registry.getToolsByCategory(ToolCategory.SEARCH).size());
        assertEquals(0, registry.getToolsByCategory(ToolCategory.ADMIN).size());
    }

    @Test
    @DisplayName("Should enable and disable tools dynamically")
    void testEnableDisableTool() {
        assertTrue(registry.isToolEnabled("test_tool"));
        registry.setToolEnabled("test_tool", false);
        assertFalse(registry.isToolEnabled("test_tool"));
    }

    @Test
    @DisplayName("Should report tool health status correctly")
    void testToolHealth() {
        Map<String, Boolean> health = registry.checkHealth();
        assertTrue(health.get("test_tool"));
    }
}
