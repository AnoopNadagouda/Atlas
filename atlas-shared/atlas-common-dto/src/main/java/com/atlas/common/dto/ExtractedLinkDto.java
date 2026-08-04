package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtractedLinkDto {
    private String sourceUrl;
    private String targetUrl;
    private String anchorText;
    private boolean isExternal;
}
