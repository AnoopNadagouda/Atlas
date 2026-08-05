package com.atlas.agent.tool.builtin;

import com.atlas.agent.client.AtlasServiceClient;
import com.atlas.domain.agent.tool.ToolCategory;
import com.atlas.domain.agent.tool.ToolMetadata;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class VectorSearchToolTest {

    private AtlasServiceClient client;
    private VectorSearchTool tool;

    @BeforeEach
    void setUp() {
        client = mock(AtlasServiceClient.class);
        tool = new VectorSearchTool(client);
    }

    @Test
    @DisplayName("Should expose valid Vector search tool metadata")
    void testMetadata() {
        ToolMetadata meta = tool.getMetadata();
        assertEquals("vector_search_tool", meta.getId());
        assertEquals(ToolCategory.SEARCH, meta.getCategory());
    }

    @Test
    @DisplayName("Should execute Vector search via AtlasServiceClient")
    void testExecution() throws Exception {
        when(client.executeVectorSearch(anyString(), anyInt()))
                .thenReturn(Map.of("query", "embedding", "mode", "vector"));

        Map<String, Object> output = tool.execute(Map.of("query", "embedding", "topK", 5));

        assertNotNull(output);
        assertEquals("vector", output.get("mode"));
        verify(client, times(1)).executeVectorSearch("embedding", 5);
    }
}
