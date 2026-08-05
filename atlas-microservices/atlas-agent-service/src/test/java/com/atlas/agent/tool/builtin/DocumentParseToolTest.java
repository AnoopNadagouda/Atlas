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

class DocumentParseToolTest {

    private AtlasServiceClient client;
    private DocumentParseTool tool;

    @BeforeEach
    void setUp() {
        client = mock(AtlasServiceClient.class);
        tool = new DocumentParseTool(client);
    }

    @Test
    @DisplayName("Should expose valid DocumentParse tool metadata")
    void testMetadata() {
        ToolMetadata meta = tool.getMetadata();
        assertEquals("document_parse_tool", meta.getId());
        assertEquals(ToolCategory.PARSE, meta.getCategory());
    }

    @Test
    @DisplayName("Should parse document via AtlasServiceClient")
    void testExecution() throws Exception {
        when(client.parseDocument(anyString(), anyString()))
                .thenReturn(Map.of("cleanText", "Hello World", "url", "https://example.com"));

        Map<String, Object> output = tool.execute(Map.of("html", "<h1>Hello World</h1>", "url", "https://example.com"));

        assertNotNull(output);
        assertEquals("Hello World", output.get("cleanText"));
        verify(client, times(1)).parseDocument("<h1>Hello World</h1>", "https://example.com");
    }
}
