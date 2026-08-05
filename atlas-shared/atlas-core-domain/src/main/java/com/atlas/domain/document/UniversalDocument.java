package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Universal Unified Document Model for multi-modal document intelligence.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UniversalDocument {
    private String documentId;
    private String tenantId;
    private String title;
    private String fileType; // PDF, DOCX, PPTX, XLSX, CSV, MARKDOWN, HTML, TXT, JSON, XML, EPUB, RTF, IMAGE, AUDIO, VIDEO
    private String mimeType;
    private long sizeBytes;
    private String extractedText;
    private double ocrConfidenceScore;
    private Instant createdAt;
    private MediaAsset mediaAsset;
    
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    @Builder.Default
    private List<String> sections = new ArrayList<>();

    // Multi-Modal Document Additions
    @Builder.Default
    private List<DocumentSection> structuredSections = new ArrayList<>();

    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    @Builder.Default
    private List<ContentFragment> contentFragments = new ArrayList<>();

    private MetadataRegistry metadataRegistry;
    private Integer pageCount;
    private String language;
    private String version;
}
