package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Request payload for federated query execution across internal index and enterprise connectors.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FederatedSearchRequest {
    private String query;
    private String userId;
    private String tenantId;
    
    @Builder.Default
    private List<String> targetConnectors = new ArrayList<>();

    @Builder.Default
    private List<String> aclTokens = new ArrayList<>();

    private int page;
    private int size;
    private int timeoutMs;
}
