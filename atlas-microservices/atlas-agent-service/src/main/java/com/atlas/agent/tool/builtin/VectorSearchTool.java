package com.atlas.agent.tool.builtin;

import com.atlas.agent.client.AtlasServiceClient;
import com.atlas.domain.agent.tool.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class VectorSearchTool implements AgentTool {

    private final AtlasServiceClient atlasServiceClient;
    private final ToolMetadata metadata;

    public VectorSearchTool(AtlasServiceClient atlasServiceClient) {
        this.atlasServiceClient = atlasServiceClient;
        this.metadata = ToolMetadata.builder()
                .id("vector_search_tool")
                .displayName("Vector Similarity Search Tool")
                .description("Executes dense embedding vector similarity searches across multi-modal document indices.")
                .version("1.0.0")
                .category(ToolCategory.SEARCH)
                .permissions(Set.of(ToolPermission.SEARCH_EXECUTE))
                .parameters(List.of(
                        ToolParameter.builder().name("query").type("string").description("Semantic query text").required(true).build(),
                        ToolParameter.builder().name("topK").type("integer").description("Top K nearest neighbors").required(false).defaultValue(5).build()
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
        int topK = parameters.containsKey("topK") ? ((Number) parameters.get("topK")).intValue() : 5;

        return atlasServiceClient.executeVectorSearch(query, topK);
    }
}
