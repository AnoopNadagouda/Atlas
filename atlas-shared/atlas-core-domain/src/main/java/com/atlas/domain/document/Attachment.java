package com.atlas.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Represents an embedded attachment within a document.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attachment {
    private String attachmentId;
    private String filename;
    private String mimeType;
    private long sizeBytes;
    private String contentHash;
    private String parentDocumentId;
    private Map<String, Object> properties;
}
