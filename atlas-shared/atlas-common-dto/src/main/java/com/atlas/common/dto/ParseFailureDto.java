package com.atlas.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParseFailureDto {
    private String id;
    private String docId;
    private String url;
    private String reason;
    private String errorMessage;
    private Instant failedAt;
}
