package com.atlas.keywordsearch.service;

import com.atlas.common.dto.PageResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.common.dto.SearchStatisticsDto;
import com.atlas.keywordsearch.engine.BM25Ranker;
import com.atlas.keywordsearch.engine.SegmentLookupEngine;
import com.atlas.keywordsearch.engine.SnippetGenerator;
import com.atlas.keywordsearch.query.ParsedQuery;
import com.atlas.keywordsearch.query.QueryParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchEngineService {

    private final QueryParser queryParser;
    private final SegmentLookupEngine lookupEngine;
    private final BM25Ranker bm25Ranker;
    private final SnippetGenerator snippetGenerator;
    private final SearchCacheService cacheService;

    public PageResponse<SearchResultDto> search(SearchRequest request) {
        long startTime = System.currentTimeMillis();

        if (request.getQuery() == null || request.getQuery().isBlank()) {
            return PageResponse.<SearchResultDto>builder()
                    .content(List.of())
                    .pageNumber(request.getPage())
                    .pageSize(request.getSize())
                    .totalElements(0)
                    .totalPages(0)
                    .last(true)
                    .build();
        }

        // 1. Check Redis Cache
        PageResponse<SearchResultDto> cached = cacheService.getCachedResponse(request.getQuery(), request.getPage(), request.getSize());
        if (cached != null) {
            return cached;
        }

        // 2. Parse Query
        ParsedQuery parsedQuery = queryParser.parse(request.getQuery());
        log.info("Executing BM25 search for query: '{}', parsed terms: {}", request.getQuery(), parsedQuery.getNormalizedTerms());

        // 3. Inverted Index Lookup
        SegmentLookupEngine.LookupResult lookupResult = lookupEngine.lookup(parsedQuery);

        // 4. BM25 Ranking
        List<BM25Ranker.ScoredDocument> rankedDocs = bm25Ranker.rank(parsedQuery, lookupResult);

        // 5. Build Search Results
        List<SearchResultDto> resultList = new ArrayList<>();
        for (BM25Ranker.ScoredDocument doc : rankedDocs) {
            String title = "Document " + doc.getDocId();
            String dummyBody = "Atlas distributed search engine document content for ID " + doc.getDocId() + " matching terms " + doc.getMatchedTerms();
            String snippet = snippetGenerator.generateSnippet(dummyBody, doc.getMatchedTerms());

            SearchResultDto resultDto = SearchResultDto.builder()
                    .id(doc.getDocId())
                    .url("https://atlas.internal/doc/" + doc.getDocId())
                    .title(title)
                    .snippet(snippet)
                    .score(doc.getBm25Score())
                    .bm25Score(doc.getBm25Score())
                    .matchedTerms(doc.getMatchedTerms())
                    .matchedFields(doc.getMatchedFields())
                    .termContributions(doc.getTermContributions())
                    .build();

            resultList.add(resultDto);
        }

        // 6. Pagination
        int totalElements = resultList.size();
        int fromIndex = Math.min(request.getPage() * request.getSize(), totalElements);
        int toIndex = Math.min(fromIndex + request.getSize(), totalElements);

        List<SearchResultDto> pagedContent = resultList.subList(fromIndex, toIndex);
        int totalPages = (int) Math.ceil((double) totalElements / Math.max(1, request.getSize()));

        PageResponse<SearchResultDto> response = PageResponse.<SearchResultDto>builder()
                .content(pagedContent)
                .pageNumber(request.getPage())
                .pageSize(request.getSize())
                .totalElements(totalElements)
                .totalPages(totalPages)
                .last(request.getPage() >= totalPages - 1)
                .build();

        // 7. Cache in Redis
        cacheService.cacheResponse(request.getQuery(), request.getPage(), request.getSize(), response);

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("BM25 Search completed in {} ms, total results: {}", executionTime, totalElements);

        return response;
    }

    public SearchStatisticsDto getStatistics() {
        var cacheStats = cacheService.getCacheStats();
        return SearchStatisticsDto.builder()
                .totalQueriesExecuted((long) cacheStats.get("totalQueries"))
                .averageLatencyMs(15.0)
                .cacheHits((long) cacheStats.get("cacheHits"))
                .cacheMisses((long) cacheStats.get("cacheMisses"))
                .cacheHitRatio((double) cacheStats.get("cacheHitRatio"))
                .totalDocumentsInCollection(100)
                .build();
    }
}
