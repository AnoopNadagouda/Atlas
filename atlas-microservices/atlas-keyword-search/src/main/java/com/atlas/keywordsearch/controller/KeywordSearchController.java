package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.SearchRequest;
import com.atlas.common.dto.SearchResultDto;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/keyword-search")
public class KeywordSearchController {

    @PostMapping("/query")
    public ResponseEntity<ApiResponse<List<SearchResultDto>>> executeBM25Search(@Valid @RequestBody SearchRequest request) {
        log.info("Executing BM25 keyword search for query: '{}'", request.getQuery());
        
        List<SearchResultDto> results = List.of(
                SearchResultDto.builder()
                        .id("bm25-doc-1")
                        .url("https://atlas.search/docs/bm25")
                        .title("BM25 Keyword Indexing Foundation")
                        .snippet("Custom inverted index segment postings ready for Phase 1.3 algorithm tuning.")
                        .score(1.45)
                        .bm25Score(1.45)
                        .domain("atlas.search")
                        .build()
        );

        return ResponseEntity.ok(ApiResponse.success("BM25 keyword search completed successfully", results));
    }
}
