package com.atlas.domain.document.parser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Technical metadata for parser input file streams.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParserMetadata {
    private String filename;
    private String fileType;
    private String mimeType;
    private String encoding;
    private long sizeBytes;

    @Builder.Default
    private Map<String, Object> properties = new HashMap<>();
}
