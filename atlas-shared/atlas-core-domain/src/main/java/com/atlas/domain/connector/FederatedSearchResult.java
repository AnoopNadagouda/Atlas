package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * Unified Federated Search Result Model representing items retrieved from internal index
 * or external enterprise connectors with source attribution and ACL permission tracking.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FederatedSearchResult {
    private String resultId;
    private String title;
    private String snippet;
    private String documentUrl;
    private double score;
    
    // Attribution & Provenance
    private String sourceName; // e.g. "GitHub", "Confluence", "Internal Index"
    private String connectorId;
    private String repository;
    private String workspace;
    private String tenantId;
    private String owner;
    private Instant lastSync;

    // Security & ACLs
    private String permissionStatus; // ALLOWED, RESTRICTED, PUBLIC
    private String aclStatus; // VERIFIED, BYPASS_ADMIN, ROLE_MATCH
    
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();
}
