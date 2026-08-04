package com.atlas.keywordsearch.vector;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.domain.vector.DistanceMetric;
import com.atlas.domain.vector.VectorSearchResult;
import com.atlas.keywordsearch.engine.SnippetGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class SemanticSearchService {

    private final EmbeddingService embeddingService;
    private final InMemHnswVectorStore vectorStore;
    private final SnippetGenerator snippetGenerator;

    public PageResponse<SearchResultDto> search(SearchRequest request) {
        if (request == null || request.getQuery() == null || request.getQuery().isBlank()) {
            return PageResponse.<SearchResultDto>builder()
                    .content(List.of())
                    .pageNumber(0)
                    .pageSize(10)
                    .totalElements(0)
                    .totalPages(0)
                    .last(true)
                    .build();
        }

        log.info("Executing Semantic Search for query: '{}'", request.getQuery());
        float[] queryVector = embeddingService.generateEmbedding(request.getQuery());

        List<VectorSearchResult> nearest = vectorStore.searchNearest(queryVector, 20, DistanceMetric.COSINE);

        List<SearchResultDto> results = new ArrayList<>();
        for (VectorSearchResult res : nearest) {
            Map<String, Object> payload = res.getPayload();
            String title = (String) payload.getOrDefault("title", "Document " + res.getId());
            String url = (String) payload.getOrDefault("url", "https://atlas.internal/doc/" + res.getId());
            String body = "Semantic vector search match for ID " + res.getId() + " with similarity " + String.format("%.4f", res.getSimilarityScore());
            String snippet = snippetGenerator.generateSnippet(body, Set.of());

            results.add(SearchResultDto.builder()
                    .id(res.getId())
                    .url(url)
                    .title(title)
                    .snippet(snippet)
                    .score(res.getSimilarityScore())
                    .vectorScore(res.getSimilarityScore())
                    .matchedTerms(Set.of("semantic-vector-match"))
                    .build());
        }

        int totalElements = results.size();
        int fromIndex = Math.min(request.getPage() * request.getSize(), totalElements);
        int toIndex = Math.min(fromIndex + request.getSize(), totalElements);

        List<SearchResultDto> pagedContent = results.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) totalElements / Math.max(1, request.getSize()));

        return PageResponse.<SearchResultDto>builder()
                .content(pagedContent)
                .pageNumber(request.getPage())
                .pageSize(request.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(request.getPage() >= totalPages - 1)
                .build();
    }
}
