package com.atlas.keywordsearch.cluster;

import com.atlas.common.dto.PageResponse;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasClusterProperties;
import com.atlas.keywordsearch.hybrid.HybridSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class SearchCoordinatorTest {

    private SearchCoordinator searchCoordinator;
    private HybridSearchService hybridSearchService;

    @BeforeEach
    void setUp() {
        AtlasClusterProperties properties = new AtlasClusterProperties();
        ClusterManager clusterManager = new ClusterManager(properties);
        clusterManager.registerLocalNode();

        HashShardingStrategy shardingStrategy = new HashShardingStrategy();
        hybridSearchService = Mockito.mock(HybridSearchService.class);

        searchCoordinator = new SearchCoordinator(clusterManager, shardingStrategy, hybridSearchService);
    }

    @Test
    void testExecuteDistributedSearch() {
        SearchResultDto dto = SearchResultDto.builder()
                .id("doc-1")
                .title("Atlas Architecture")
                .snippet("Distributed Search Engine")
                .score(0.95)
                .build();

        PageResponse<SearchResultDto> mockPage = PageResponse.<SearchResultDto>builder()
                .content(List.of(dto))
                .pageNumber(0)
                .pageSize(10)
                .totalElements(1)
                .build();

        when(hybridSearchService.searchHybrid(any())).thenReturn(mockPage);

        List<SearchResultDto> results = searchCoordinator.executeDistributedSearch("atlas", 10);
        assertNotNull(results);
        assertEquals(1, results.size());
        assertEquals("doc-1", results.get(0).getId());
    }
}
