package com.atlas.keywordsearch.controller;

import com.atlas.common.dto.ApiResponse;
import com.atlas.common.dto.SearchResultDto;
import com.atlas.domain.history.DocumentVersion;
import com.atlas.domain.history.IndexSnapshot;
import com.atlas.domain.history.VersionDiff;
import com.atlas.keywordsearch.history.DifferenceEngine;
import com.atlas.keywordsearch.history.SnapshotManager;
import com.atlas.keywordsearch.history.TimeTravelQueryPlanner;
import com.atlas.keywordsearch.history.VersionedDocumentStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v10/history")
@RequiredArgsConstructor
public class HistoryController {

    private final VersionedDocumentStore documentStore;
    private final SnapshotManager snapshotManager;
    private final DifferenceEngine differenceEngine;
    private final TimeTravelQueryPlanner queryPlanner;

    @GetMapping("/document/{id}")
    public ResponseEntity<ApiResponse<List<DocumentVersion>>> getDocumentHistory(@PathVariable String id) {
        log.info("Fetching document version history for ID: '{}'", id);
        return ResponseEntity.ok(ApiResponse.success(documentStore.getDocumentHistory(id)));
    }

    @GetMapping("/snapshots")
    public ResponseEntity<ApiResponse<List<IndexSnapshot>>> getAllSnapshots() {
        log.info("Fetching all index snapshots");
        return ResponseEntity.ok(ApiResponse.success(snapshotManager.getAllSnapshots()));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<SearchResultDto>>> executeTimeTravelSearch(
            @RequestParam String query,
            @RequestParam(required = false) String timestamp) {
        log.info("Executing Time Travel Search for query '{}' at timestamp {}", query, timestamp);
        Instant target = timestamp != null ? Instant.parse(timestamp) : Instant.now();
        List<SearchResultDto> results = queryPlanner.executeTimeTravelSearch(query, target);
        return ResponseEntity.ok(ApiResponse.success(results));
    }

    @GetMapping("/diff")
    public ResponseEntity<ApiResponse<VersionDiff>> getVersionDiff(
            @RequestParam String docId,
            @RequestParam String v1Id,
            @RequestParam String v2Id) {
        log.info("Computing version diff for doc '{}' between {} and {}", docId, v1Id, v2Id);
        List<DocumentVersion> history = documentStore.getDocumentHistory(docId);
        DocumentVersion v1 = history.stream().filter(v -> v.getVersionId().equals(v1Id)).findFirst().orElse(null);
        DocumentVersion v2 = history.stream().filter(v -> v.getVersionId().equals(v2Id)).findFirst().orElse(null);

        VersionDiff diff = differenceEngine.computeDiff(v1, v2);
        return ResponseEntity.ok(ApiResponse.success(diff));
    }

    @PostMapping("/snapshot/create")
    public ResponseEntity<ApiResponse<IndexSnapshot>> createSnapshot(
            @RequestParam String id,
            @RequestParam long docs,
            @RequestParam long bytes) {
        log.info("Triggering manual snapshot creation: '{}'", id);
        IndexSnapshot snapshot = snapshotManager.createSnapshot(id, Instant.now(), docs, bytes);
        return ResponseEntity.ok(ApiResponse.success(snapshot));
    }
}
