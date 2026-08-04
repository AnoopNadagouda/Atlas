package com.atlas.keywordsearch.hybrid;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.config.AtlasHybridProperties;
import com.atlas.keywordsearch.engine.SnippetGenerator;
import com.atlas.keywordsearch.pipeline.SearchPipelineOrchestrator;
import com.atlas.keywordsearch.vector.InMemHnswVectorStore;
import com.atlas.keywordsearch.vector.LocalTransformerEmbeddingProvider;
import com.atlas.keywordsearch.vector.SemanticSearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HybridSearchServiceTest {

    private HybridSearchService hybridSearchService;

    @BeforeEach
    void setUp() {
        SearchPipelineOrchestrator mockOrchestrator = Mockito.mock(SearchPipelineOrchestrator.class);
        LocalTransformerEmbeddingProvider provider = new LocalTransformerEmbeddingProvider();
        InMemHnswVectorStore vectorStore = new InMemHnswVectorStore();
        SnippetGenerator snippetGenerator = new SnippetGenerator();

        SemanticSearchService semanticSearchService = new SemanticSearchService(
                new com.atlas.keywordsearch.vector.EmbeddingService(provider),
                vectorStore,
                snippetGenerator
        );

        AtlasHybridProperties properties = new AtlasHybridProperties();
        properties.setRrfK(60);
        properties.setTimeoutMs(3000);

        ReciprocalRankFusionEngine rrfEngine = new ReciprocalRankFusionEngine(properties);

        // Mock BM25 Orchestrator response
        SearchResultDto bm25Doc = SearchResultDto.builder()
                .id("doc-bm25-1")
                .title("BM25 Match")
                .url("https://atlas.internal/bm25")
                .bm25Score(0.95)
                .build();

        PageResponse<SearchResultDto> bm25Page = PageResponse.<SearchResultDto>builder()
                .content(List.of(bm25Doc))
                .totalElements(1)
                .build();

        when(mockOrchestrator.executePipeline(any())).thenReturn(bm25Page);

        // Populate vector store for semantic match
        float[] docVec = provider.embedText("Atlas hybrid search engine");
        vectorStore.storeVector("doc-vec-2", docVec, Map.of("title", "Vector Match", "url", "https://atlas.internal/vector"));

        hybridSearchService = new HybridSearchService(mockOrchestrator, semanticSearchService, rrfEngine, properties);
    }

    @Test
    void testParallelHybridSearchExecution() {
        SearchRequest request = SearchRequest.builder()
                .query("hybrid search")
                .page(0)
                .size(10)
                .build();

        PageResponse<SearchResultDto> response = hybridSearchService.searchHybrid(request);

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertTrue(response.getContent().size() >= 1);
    }
}
