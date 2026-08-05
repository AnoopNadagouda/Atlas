package com.atlas.domain.connector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * Enterprise connector authentication configuration.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConnectorAuthentication {
    private String authType; // OAUTH2, BEARER_TOKEN, API_KEY, BASIC, PAT
    private String clientId;
    private String encryptedClientSecret;
    private String encryptedAccessToken;
    private String encryptedRefreshToken;
    private String apiKey;
    private String username;
    private String encryptedPassword;
    private String personalAccessToken;
    private String tokenEndpointUrl;

    @Builder.Default
    private Map<String, String> customHeaders = new HashMap<>();
}
