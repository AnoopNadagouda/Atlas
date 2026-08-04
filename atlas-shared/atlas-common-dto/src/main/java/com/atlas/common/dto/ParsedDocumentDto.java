package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedDocumentDto {
    private String id;
    private String jobId;
    private String url;
    private String normalizedUrl;
    private String canonicalUrl;
    private String domain;
    private String title;
    private String description;
    private String cleanTextPreview;
    private String language;
    private double languageConfidence;
    private boolean isDuplicate;
    private String duplicateType;
    private String duplicateOfDocId;
    private long simhash;
    private String contentHash;
    private int contentLength;
    private Instant processedAt;
}
