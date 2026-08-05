package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents an extracted fragment of content (text block, code snippet, table, caption).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentFragment {
    private String fragmentId;
    private String fragmentType; // TEXT, CODE, TABLE, IMAGE_CAPTION, AUDIO_TRANSCRIPT
    private int startOffset;
    private int endOffset;
    private String textContent;
    private double confidenceScore;
    private Map<String, Object> metadata;
}
