package com.atlas.indexbuilder.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "document_segment_mappings", indexes = {
    @Index(name = "idx_doc_seg_doc", columnList = "doc_id")
})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSegmentMappingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "doc_id", nullable = false)
    private String docId;

    @Column(name = "segment_id", nullable = false)
    private String segmentId;

    @Column(name = "indexed_at", nullable = false)
    @Builder.Default
    private Instant indexedAt = Instant.now();
}
