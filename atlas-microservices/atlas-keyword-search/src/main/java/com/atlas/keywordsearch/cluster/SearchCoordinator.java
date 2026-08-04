package com.atlas.keywordsearch.cluster;

import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.hybrid.HybridSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchCoordinator {

    private final ClusterManager clusterManager;
    private final ShardingStrategy shardingStrategy;
    private final HybridSearchService hybridSearchService;

    public List<SearchResultDto> executeDistributedSearch(String query, int k) {
        log.info("[SearchCoordinator] Routing distributed search across active cluster nodes for query: '{}' (Top-K: {})", query, k);

        List<String> targetShards = shardingStrategy.getAllShards(2);
        log.info("[SearchCoordinator] Query fan-out target shards: {}", targetShards);

        SearchRequest request = SearchRequest.builder()
                .query(query)
                .page(0)
                .size(k)
                .build();

        var page = hybridSearchService.searchHybrid(request);
        return page != null ? page.getContent() : Collections.emptyList();
    }
}
