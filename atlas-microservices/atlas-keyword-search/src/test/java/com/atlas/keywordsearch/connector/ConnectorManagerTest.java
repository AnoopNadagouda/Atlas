package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorConfiguration;
import com.atlas.domain.connector.ConnectorHealth;
import com.atlas.domain.connector.ConnectorMetadata;

import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import com.atlas.keywordsearch.connector.adapters.ConfluenceConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorManagerTest {

    private ConnectorManager connectorManager;

    @BeforeEach
    void setUp() {
        ConnectorRegistry registry = new ConnectorRegistry(List.of(new GitHubConnector(), new ConfluenceConnector()));
        registry.registerInjectedConnectors();
        CredentialEncryptor encryptor = new CredentialEncryptor();
        SecretRotationService rotationService = new SecretRotationService(encryptor);
        ConnectorScheduler scheduler = new ConnectorScheduler(registry, null);
        SyncJobHistoryStore historyStore = new SyncJobHistoryStore();
        SyncDeadLetterQueue dlq = new SyncDeadLetterQueue();
        SyncEngineService syncEngineService = new SyncEngineService(registry, null, historyStore, dlq);

        connectorManager = new ConnectorManager(registry, scheduler, rotationService, syncEngineService);
    }

    @Test
    void testRegisterConnector() {
        ConnectorConfiguration config = ConnectorConfiguration.builder()
                .connectorId("github")
                .name("Test GitHub")
                .endpointUrl("https://api.github.com")
                .build();

        ConnectorConfiguration registered = connectorManager.registerConnector(config, "secret-token");
        assertNotNull(registered);
        assertEquals("github", registered.getConnectorId());
    }

    @Test
    void testCheckHealth() {
        ConnectorHealth health = connectorManager.checkHealth("github");
        assertNotNull(health);
        assertEquals("UP", health.getStatus());
    }
}
