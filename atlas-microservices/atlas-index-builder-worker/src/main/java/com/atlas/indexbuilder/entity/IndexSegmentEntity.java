package com.atlas.indexbuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "index_segments")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IndexSegmentEntity {

    @Id
    private String id;

    @Column(name = "segment_name", nullable = false)
    private String segmentName;

    @Column(name = "document_count")
    private long documentCount;

    @Column(name = "vocabulary_size")
    private long vocabularySize;

    @Column(name = "total_term_count")
    private long totalTermCount;

    @Column(name = "storage_path", nullable = false, length = 1024)
    private String storagePath;

    @Column(nullable = false, length = 20)
    @Builder.Default
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false)
    @Builder.Default
    private Instant createdAt = Instant.now();
}
