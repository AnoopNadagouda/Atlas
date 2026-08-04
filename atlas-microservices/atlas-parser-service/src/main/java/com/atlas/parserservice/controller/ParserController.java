package com.atlas.parserservice.controller;

import com.atlas.common.dto.*;
import com.atlas.parserservice.service.ParserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/parser")
@RequiredArgsConstructor
public class ParserController {

    private final ParserService parserService;

    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<ParserStatisticsDto>> getStatistics() {
        log.info("Fetching document parser statistics");
        ParserStatisticsDto stats = parserService.getStatistics();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<ApiResponse<ParsedDocumentDto>> getDocument(@PathVariable String id) {
        log.info("Fetching parsed document details for id: '{}'", id);
        ParsedDocumentDto doc = parserService.getDocument(id);
        return ResponseEntity.ok(ApiResponse.success(doc));
    }

    @GetMapping("/duplicates")
    public ResponseEntity<ApiResponse<PageResponse<ParsedDocumentDto>>> getDuplicates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching paged duplicate documents (page: {}, size: {})", page, size);
        PageResponse<ParsedDocumentDto> duplicates = parserService.getDuplicates(page, size);
        return ResponseEntity.ok(ApiResponse.success(duplicates));
    }

    @GetMapping("/failures")
    public ResponseEntity<ApiResponse<PageResponse<ParseFailureDto>>> getFailures(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        log.info("Fetching paged parse failures (page: {}, size: {})", page, size);
        PageResponse<ParseFailureDto> failures = parserService.getFailures(page, size);
        return ResponseEntity.ok(ApiResponse.success(failures));
    }

    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> getHealth() {
        log.info("Parser Service health check requested");
        return ResponseEntity.ok(ApiResponse.success(Map.of("status", "UP", "service", "atlas-parser-service")));
    }
}
