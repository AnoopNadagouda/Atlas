package com.atlas.parserservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "parsed_links", indexes = {
    @Index(name = "idx_parsed_link_doc", columnList = "doc_id")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParsedLinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "doc_id", nullable = false)
    private String docId;

    @Column(name = "source_url", nullable = false, length = 2048)
    private String sourceUrl;

    @Column(name = "target_url", nullable = false, length = 2048)
    private String targetUrl;

    @Column(name = "anchor_text", length = 1024)
    private String anchorText;

    @Column(name = "is_external")
    private boolean isExternal;

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
