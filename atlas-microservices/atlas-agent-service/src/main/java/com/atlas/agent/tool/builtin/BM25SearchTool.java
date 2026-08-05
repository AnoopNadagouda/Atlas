package com.atlas.agent.tool.builtin;

import com.atlas.agent.client.AtlasServiceClient;
import com.atlas.domain.agent.tool.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class BM25SearchTool implements AgentTool {

    private final AtlasServiceClient atlasServiceClient;
    private final ToolMetadata metadata;

    public BM25SearchTool(AtlasServiceClient atlasServiceClient) {
        this.atlasServiceClient = atlasServiceClient;
        this.metadata = ToolMetadata.builder()
                .id("bm25_search_tool")
                .displayName("BM25 Keyword Search Tool")
                .description("Executes full-text BM25 query searches across indexed web documents in Atlas.")
                .version("1.0.0")
                .category(ToolCategory.SEARCH)
                .permissions(Set.of(ToolPermission.SEARCH_EXECUTE))
                .parameters(List.of(
                        ToolParameter.builder().name("query").type("string").description("The search query string").required(true).build(),
                        ToolParameter.builder().name("page").type("integer").description("Zero-indexed page number").required(false).defaultValue(0).build(),
                        ToolParameter.builder().name("size").type("integer").description("Number of results per page").required(false).defaultValue(10).build()
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
        String query = (String) parameters.get("query");
        int page = parameters.containsKey("page") ? ((Number) parameters.get("page")).intValue() : 0;
        int size = parameters.containsKey("size") ? ((Number) parameters.get("size")).intValue() : 10;

        return atlasServiceClient.executeSearch(query, page, size);
    }
}
