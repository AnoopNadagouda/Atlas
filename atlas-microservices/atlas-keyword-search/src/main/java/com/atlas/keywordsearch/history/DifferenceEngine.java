package com.atlas.keywordsearch.history;

import com.atlas.domain.history.DocumentVersion;
import com.atlas.domain.history.VersionDiff;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DifferenceEngine {

    public VersionDiff computeDiff(DocumentVersion v1, DocumentVersion v2) {
        if (v1 == null || v2 == null) return null;

        List<String> added = List.of(v2.getTitle(), v2.getSnippet());
        List<String> removed = List.of(v1.getTitle(), v1.getSnippet());

        return VersionDiff.builder()
                .docId(v1.getDocId())
                .fromVersionId(v1.getVersionId())
                .toVersionId(v2.getVersionId())
                .addedContent(added)
                .removedContent(removed)
                .similarityScore(0.82)
                .build();
    }
}
