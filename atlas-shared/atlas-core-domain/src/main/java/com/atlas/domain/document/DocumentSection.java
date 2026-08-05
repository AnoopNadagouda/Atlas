package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Represents a structured section within a document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSection {
    private String sectionId;
    private int sectionIndex;
    private String title;
    private int level;
    private int pageNumber;
    private String content;
    private List<DocumentSection> childSections;
    private Map<String, Object> attributes;
}
