package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.Connector;
import com.atlas.keywordsearch.connector.adapters.GitHubConnector;
import com.atlas.keywordsearch.connector.adapters.SlackConnector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConnectorRegistryTest {

    private ConnectorRegistry connectorRegistry;

    @BeforeEach
    void setUp() {
        connectorRegistry = new ConnectorRegistry(List.of(new GitHubConnector(), new SlackConnector()));
        connectorRegistry.registerInjectedConnectors();
    }

    @Test
    void testGetConnector() {
        Connector gh = connectorRegistry.getConnector("github");
        assertNotNull(gh);
        assertEquals("github", gh.getMetadata().getConnectorId());

        Connector slack = connectorRegistry.getConnector("slack");
        assertNotNull(slack);
        assertEquals("slack", slack.getMetadata().getConnectorId());
    }

    @Test
    void testGetAllMetadata() {
        var metadataList = connectorRegistry.getAllMetadata();
        assertNotNull(metadataList);
        assertEquals(2, metadataList.size());
    }
}
