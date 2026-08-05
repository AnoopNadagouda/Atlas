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

class BM25SearchToolTest {

    private AtlasServiceClient client;
    private BM25SearchTool tool;

    @BeforeEach
    void setUp() {
        client = mock(AtlasServiceClient.class);
        tool = new BM25SearchTool(client);
    }

    @Test
    @DisplayName("Should expose valid BM25 tool metadata")
    void testMetadata() {
        ToolMetadata meta = tool.getMetadata();
        assertEquals("bm25_search_tool", meta.getId());
        assertEquals(ToolCategory.SEARCH, meta.getCategory());
    }

    @Test
    @DisplayName("Should execute BM25 search via AtlasServiceClient")
    void testExecution() throws Exception {
        when(client.executeSearch(anyString(), anyInt(), anyInt()))
                .thenReturn(Map.of("query", "distributed", "totalHits", 1));

        Map<String, Object> output = tool.execute(Map.of("query", "distributed", "page", 0, "size", 10));

        assertNotNull(output);
        assertEquals("distributed", output.get("query"));
        verify(client, times(1)).executeSearch("distributed", 0, 10);
    }
}
