package com.atlas.agent.tool.builtin;

import com.atlas.agent.client.AtlasServiceClient;
import com.atlas.domain.agent.tool.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class DocumentParseTool implements AgentTool {

    private final AtlasServiceClient atlasServiceClient;
    private final ToolMetadata metadata;

    public DocumentParseTool(AtlasServiceClient atlasServiceClient) {
        this.atlasServiceClient = atlasServiceClient;
        this.metadata = ToolMetadata.builder()
                .id("document_parse_tool")
                .displayName("HTML Document Parser & Cleaner Tool")
                .description("Parses raw HTML, strips boilerplate node elements, extracts clean text, and detects metadata.")
                .version("1.0.0")
                .category(ToolCategory.PARSE)
                .permissions(Set.of(ToolPermission.PARSE_EXECUTE))
                .parameters(List.of(
                        ToolParameter.builder().name("html").type("string").description("Raw HTML document string to parse").required(true).build(),
                        ToolParameter.builder().name("url").type("string").description("Source URL of document").required(false).defaultValue("https://example.com").build()
                ))
                .timeoutMs(10000L)
                .build();
    }

    @Override
    public ToolMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> parameters) throws Exception {
        String html = (String) parameters.get("html");
        String url = (String) parameters.getOrDefault("url", "https://example.com");

        return atlasServiceClient.parseDocument(html, url);
    }
}
