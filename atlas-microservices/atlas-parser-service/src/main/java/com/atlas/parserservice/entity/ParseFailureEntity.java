package com.atlas.parserservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "parse_failures")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParseFailureEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "doc_id")
    private String docId;

    @Column(length = 2048)
    private String url;

    @Column(nullable = false, length = 255)
    private String reason;

    @Column(name = "error_message", length = 2048)
    private String errorMessage;

    @Column(name = "failed_at", nullable = false)
    @Builder.Default
    private Instant failedAt = Instant.now();
}
