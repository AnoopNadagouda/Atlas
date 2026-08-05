package com.atlas.domain.tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiKey {
    private String keyId;
    private String tenantId;
    private String name;
    private String secretKey;
    private String role; // READ_ONLY, CRAWLER, ADMIN, AI, SEARCH
    private Instant createdAt;
    private Instant expiresAt;
    private boolean revoked;
}
