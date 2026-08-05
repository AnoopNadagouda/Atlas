package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorAuthentication;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Secret rotation service managing credential re-encryption and secret updates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SecretRotationService {

    private final CredentialEncryptor encryptor;
    private final Map<String, ConnectorAuthentication> authStore = new ConcurrentHashMap<>();

    public ConnectorAuthentication rotateSecrets(String connectorId, String newRawSecret) {
        log.info("[SecretRotationService] Rotating credential secret for connector '{}'", connectorId);
        String encrypted = encryptor.encrypt(newRawSecret);

        ConnectorAuthentication updated = ConnectorAuthentication.builder()
                .authType("BEARER_TOKEN")
                .encryptedAccessToken(encrypted)
                .apiKey(encrypted)
                .personalAccessToken(encrypted)
                .build();

        authStore.put(connectorId, updated);
        return updated;
    }

    public ConnectorAuthentication getAuthentication(String connectorId) {
        return authStore.getOrDefault(connectorId, ConnectorAuthentication.builder().authType("API_KEY").apiKey("default-key").build());
    }
}
