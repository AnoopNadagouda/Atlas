package com.atlas.indexbuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "index_statistics")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexStatisticsEntity {

    @Id
    private String id;

    @Column(name = "total_documents_indexed")
    private long totalDocumentsIndexed;

    @Column(name = "total_terms_indexed")
    private long totalTermsIndexed;

    @Column(name = "vocabulary_size")
    private long vocabularySize;

    @Column(name = "total_segments_count")
    private long totalSegmentsCount;

    @Column(name = "updated_at", nullable = false)
    @Builder.Default
    private Instant updatedAt = Instant.now();
}
