package com.atlas.agent.tool.builtin;

import com.atlas.agent.client.AtlasServiceClient;
import com.atlas.domain.agent.tool.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Component
public class WebCrawlTool implements AgentTool {

    private final AtlasServiceClient atlasServiceClient;
    private final ToolMetadata metadata;

    public WebCrawlTool(AtlasServiceClient atlasServiceClient) {
        this.atlasServiceClient = atlasServiceClient;
        this.metadata = ToolMetadata.builder()
                .id("web_crawl_tool")
                .displayName("Distributed Web Crawler Tool")
                .description("Dispatches distributed web crawl jobs to fetch raw HTML pages starting from seed URLs.")
                .version("1.0.0")
                .category(ToolCategory.CRAWL)
                .permissions(Set.of(ToolPermission.CRAWL_EXECUTE))
                .parameters(List.of(
                        ToolParameter.builder().name("seedUrl").type("string").description("Seed URL to start crawling").required(true).build(),
                        ToolParameter.builder().name("maxDepth").type("integer").description("Maximum crawl depth link traversal").required(false).defaultValue(2).build(),
                        ToolParameter.builder().name("maxPages").type("integer").description("Maximum total pages to fetch").required(false).defaultValue(50).build()
                ))
                .timeoutMs(15000L)
                .build();
    }

    @Override
    public ToolMetadata getMetadata() {
        return metadata;
    }

    @Override
    public Map<String, Object> execute(Map<String, Object> parameters) throws Exception {
        String seedUrl = (String) parameters.get("seedUrl");
        int maxDepth = parameters.containsKey("maxDepth") ? ((Number) parameters.get("maxDepth")).intValue() : 2;
        int maxPages = parameters.containsKey("maxPages") ? ((Number) parameters.get("maxPages")).intValue() : 50;

        return atlasServiceClient.dispatchCrawlJob(seedUrl, maxDepth, maxPages);
    }
}
