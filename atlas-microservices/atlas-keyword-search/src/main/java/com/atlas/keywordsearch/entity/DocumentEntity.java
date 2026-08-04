package com.atlas.keywordsearch.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "documents")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String url;

    @Column(name = "url_hash", nullable = false, length = 64)
    private String urlHash;

    private String title;

    @Column(name = "content_hash", nullable = false, length = 64)
    private String contentHash;

    @Column(nullable = false)
    private long simhash;

    @Column(name = "mime_type", nullable = false, length = 50)
    private String mimeType;

    @Column(name = "pagerank_score")
    @Builder.Default
    private double pageRankScore = 1.0;

    @Column(nullable = false)
    private String domain;

    @Column(name = "published_at")
    private Instant publishedAt;

    @Column(name = "crawled_at", nullable = false)
    @Builder.Default
    private Instant crawledAt = Instant.now();

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "INDEXED";
}
