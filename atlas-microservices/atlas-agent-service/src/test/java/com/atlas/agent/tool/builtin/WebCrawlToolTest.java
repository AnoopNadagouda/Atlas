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

class WebCrawlToolTest {

    private AtlasServiceClient client;
    private WebCrawlTool tool;

    @BeforeEach
    void setUp() {
        client = mock(AtlasServiceClient.class);
        tool = new WebCrawlTool(client);
    }

    @Test
    @DisplayName("Should expose valid WebCrawl tool metadata")
    void testMetadata() {
        ToolMetadata meta = tool.getMetadata();
        assertEquals("web_crawl_tool", meta.getId());
        assertEquals(ToolCategory.CRAWL, meta.getCategory());
    }

    @Test
    @DisplayName("Should dispatch crawl job via AtlasServiceClient")
    void testExecution() throws Exception {
        when(client.dispatchCrawlJob(anyString(), anyInt(), anyInt()))
                .thenReturn(Map.of("jobId", "crawl-100", "seedUrl", "https://example.com"));

        Map<String, Object> output = tool.execute(Map.of("seedUrl", "https://example.com", "maxDepth", 2, "maxPages", 50));

        assertNotNull(output);
        assertEquals("crawl-100", output.get("jobId"));
        verify(client, times(1)).dispatchCrawlJob("https://example.com", 2, 50);
    }
}
