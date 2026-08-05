package com.atlas.keywordsearch.history;

import com.atlas.domain.history.DocumentVersion;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class VersionedDocumentStore {

    private final Map<String, List<DocumentVersion>> versionHistory = new ConcurrentHashMap<>();

    @PostConstruct
    public void initSeedHistory() {
        log.info("Initializing Versioned Document Store with seed historical snapshots...");

        String docId = "doc-foundation-001";
        Instant now = Instant.now();

        DocumentVersion v1 = DocumentVersion.builder()
                .docId(docId)
                .versionId("v-1.0")
                .crawlTimestamp(now.minus(30, ChronoUnit.DAYS))
                .contentHash("hash-v1")
                .title("Atlas Search - Single Node BM25 Engine")
                .snippet("Phase 1.0 Custom Inverted Index and Robertson-Spärck Jones BM25 Ranker.")
                .content("Phase 1.0 Custom Inverted Index and Robertson-Spärck Jones BM25 Ranker.")
                .parentVersionId(null)
                .build();

        DocumentVersion v2 = DocumentVersion.builder()
                .docId(docId)
                .versionId("v-2.0")
                .crawlTimestamp(now.minus(15, ChronoUnit.DAYS))
                .contentHash("hash-v2")
                .title("Atlas Search - Hybrid Search & Grounded Copilot")
                .snippet("Phase 2.0 Parallel Hybrid Search combining BM25 and HNSW vectors.")
                .content("Phase 2.0 Parallel Hybrid Search combining BM25 and HNSW vectors.")
                .parentVersionId("v-1.0")
                .build();

        DocumentVersion v3 = DocumentVersion.builder()
                .docId(docId)
                .versionId("v-3.0")
                .crawlTimestamp(now)
                .contentHash("hash-v3")
                .title("Atlas Platform - Enterprise AI Search Engine")
                .snippet("Phase 4.2 Time Travel Search and Versioned Historical Indexing.")
                .content("Phase 4.2 Time Travel Search and Versioned Historical Indexing.")
                .parentVersionId("v-2.0")
                .build();

        saveVersion(v1);
        saveVersion(v2);
        saveVersion(v3);
    }

    public void saveVersion(DocumentVersion version) {
        if (version == null || version.getDocId() == null) return;
        versionHistory.computeIfAbsent(version.getDocId(), k -> new ArrayList<>()).add(version);
        log.info("[VersionedDocumentStore] Saved version '{}' for doc '{}'", version.getVersionId(), version.getDocId());
    }

    public List<DocumentVersion> getDocumentHistory(String docId) {
        return versionHistory.getOrDefault(docId, Collections.emptyList());
    }

    public DocumentVersion getVersionAtTimestamp(String docId, Instant timestamp) {
        List<DocumentVersion> history = getDocumentHistory(docId);
        if (history.isEmpty()) return null;

        DocumentVersion bestMatch = history.get(0);
        for (DocumentVersion v : history) {
            if (!v.getCrawlTimestamp().isAfter(timestamp)) {
                bestMatch = v;
            }
        }
        return bestMatch;
    }
}
