package com.atlas.keywordsearch.vector;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.keywordsearch.engine.SnippetGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SemanticSearchServiceTest {

    private SemanticSearchService semanticSearchService;
    private InMemHnswVectorStore vectorStore;

    @BeforeEach
    void setUp() {
        LocalTransformerEmbeddingProvider provider = new LocalTransformerEmbeddingProvider();
        EmbeddingService embeddingService = new EmbeddingService(provider);
        vectorStore = new InMemHnswVectorStore();
        SnippetGenerator snippetGenerator = new SnippetGenerator();

        semanticSearchService = new SemanticSearchService(embeddingService, vectorStore, snippetGenerator);

        // Store sample document
        float[] docVec = provider.embedText("Atlas cloud search platform");
        vectorStore.storeVector("doc-100", docVec, Map.of("title", "Atlas Platform", "url", "https://atlas.internal/doc/100"));
    }

    @Test
    void testSemanticSearchRetrieval() {
        SearchRequest request = SearchRequest.builder()
                .query("cloud search")
                .page(0)
                .size(10)
                .build();

        PageResponse<SearchResultDto> response = semanticSearchService.search(request);

        assertNotNull(response);
        assertFalse(response.getContent().isEmpty());
        assertEquals("doc-100", response.getContent().get(0).getId());
        assertEquals("Atlas Platform", response.getContent().get(0).getTitle());
    }
}
