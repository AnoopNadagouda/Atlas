package com.atlas.parserservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "parsed_documents", indexes = {
    @Index(name = "idx_parsed_doc_hash", columnList = "content_hash"),
    @Index(name = "idx_parsed_doc_simhash", columnList = "simhash")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedDocumentEntity {

    @Id
    private String id;

    @Column(name = "job_id")
    private String jobId;

    @Column(nullable = false, length = 2048)
    private String url;

    @Column(name = "normalized_url", nullable = false, length = 2048)
    private String normalizedUrl;

    @Column(name = "canonical_url", length = 2048)
    private String canonicalUrl;

    @Column(nullable = false, length = 255)
    private String domain;

    @Column(length = 1024)
    private String title;

    @Column(length = 2048)
    private String description;

    @Column(name = "clean_text", columnDefinition = "TEXT")
    private String cleanText;

    @Column(length = 10)
    private String language;

    @Column(name = "language_confidence")
    private double languageConfidence;

    @Column(name = "is_duplicate")
    private boolean isDuplicate;

    @Column(name = "duplicate_type", length = 30)
    private String duplicateType; // EXACT, SIMHASH, CANONICAL, NONE

    @Column(name = "duplicate_of_doc_id")
    private String duplicateOfDocId;

    @Column(name = "simhash")
    private long simhash;

    @Column(name = "content_hash", length = 64)
    private String contentHash;

    @Column(name = "content_length")
    private int contentLength;

    @Column(name = "processed_at", nullable = false)
    @Builder.Default
    private Instant processedAt = Instant.now();
}
