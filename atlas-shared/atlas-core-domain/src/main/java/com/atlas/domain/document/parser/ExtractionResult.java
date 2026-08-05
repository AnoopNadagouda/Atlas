package com.atlas.domain.document.parser;

import com.atlas.domain.document.Attachment;
import com.atlas.domain.document.ContentFragment;
import com.atlas.domain.document.DocumentSection;
import com.atlas.domain.document.MetadataRegistry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Result of document parsing and text extraction.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractionResult {
    private String documentTitle;
    private String extractedText;
    private int characterCount;
    private int wordCount;
    private int pageCount;
    private String language;
    private boolean ocrApplied;
    private double ocrConfidenceScore;
    private long extractionDurationMs;
    private String status; // SUCCESS, DEGRADED, FAILED

    @Builder.Default
    private List<DocumentSection> sections = new ArrayList<>();

    @Builder.Default
    private List<ContentFragment> contentFragments = new ArrayList<>();

    @Builder.Default
    private List<Attachment> attachments = new ArrayList<>();

    private MetadataRegistry metadataRegistry;

    @Builder.Default
    private Map<String, Object> attributes = new HashMap<>();
}
