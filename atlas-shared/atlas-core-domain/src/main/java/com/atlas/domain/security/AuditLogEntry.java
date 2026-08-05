package com.atlas.domain.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntry {
    private String id;
    private Instant timestamp;
    private String userId;
    private UserRole role;
    private String action;
    private String resource;
    private String clientIp;
    private String status;
    private String details;
}
