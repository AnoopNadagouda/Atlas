package com.atlas.testsupport;

import com.atlas.common.dto.CrawlJobRequest;
import com.atlas.common.dto.SearchRequest;
import com.atlas.domain.model.enums.QueryIntent;

import java.util.List;

public final class TestDataFactory {

    private TestDataFactory() {}

    public static SearchRequest createSampleSearchRequest() {
        return SearchRequest.builder()
                .query("distributed search engine")
                .intent(QueryIntent.KEYWORD_SEARCH)
                .page(0)
                .size(10)
                .domainFilter("atlas.search")
                .build();
    }

    public static CrawlJobRequest createSampleCrawlJobRequest() {
        return CrawlJobRequest.builder()
                .jobName("Test Crawl Job")
                .seedUrls(List.of("https://atlas.search/docs"))
                .maxDepth(3)
                .maxPages(1000)
                .build();
    }
}
