package com.atlas.domain.history;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentVersion {
    private String docId;
    private String versionId;
    private Instant crawlTimestamp;
    private String contentHash;
    private String title;
    private String snippet;
    private String content;
    private String parentVersionId;
}
