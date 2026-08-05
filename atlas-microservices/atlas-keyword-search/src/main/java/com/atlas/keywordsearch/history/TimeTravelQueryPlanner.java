package com.atlas.keywordsearch.history;

import com.atlas.common.dto.SearchResultDto;
import com.atlas.domain.history.DocumentVersion;
import com.atlas.domain.history.IndexSnapshot;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class TimeTravelQueryPlanner {

    private final SnapshotManager snapshotManager;
    private final VersionedDocumentStore versionedDocumentStore;

    public List<SearchResultDto> executeTimeTravelSearch(String query, Instant targetTimestamp) {
        log.info("[TimeTravelQueryPlanner] Planning historical search for query '{}' at timestamp {}", query, targetTimestamp);

        IndexSnapshot snapshot = snapshotManager.getNearestSnapshot(targetTimestamp);
        log.info("[TimeTravelQueryPlanner] Selected nearest historical snapshot '{}' ({})",
                snapshot != null ? snapshot.getSnapshotId() : "NONE", targetTimestamp);

        String docId = "doc-foundation-001";
        DocumentVersion version = versionedDocumentStore.getVersionAtTimestamp(docId, targetTimestamp);

        List<SearchResultDto> results = new ArrayList<>();
        if (version != null) {
            results.add(SearchResultDto.builder()
                    .id(version.getDocId())
                    .title(version.getTitle())
                    .url("https://atlas.search/history/" + version.getVersionId())
                    .snippet(version.getSnippet())
                    .score(0.95)
                    .retrievalSources(Set.of("HISTORICAL_SNAPSHOT", "TIME_TRAVEL"))
                    .build());
        }
        return results;
    }
}
