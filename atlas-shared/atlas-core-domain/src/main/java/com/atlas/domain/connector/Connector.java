package com.atlas.domain.connector;

import com.atlas.domain.document.UniversalDocument;

import java.util.List;

/**
 * Universal SPI interface for enterprise connector adapters.
 */
public interface Connector {

    /**
     * Initializes the connector instance with configuration and authentication settings.
     */
    void initialize(ConnectorConfiguration config, ConnectorAuthentication auth);

    /**
     * Establishes connection to the remote enterprise system.
     *
     * @return true if successfully connected
     */
    boolean connect();

    /**
     * Disconnects and releases any open network connections.
     */
    void disconnect();

    /**
     * Executes synchronization job (FULL or INCREMENTAL) and streams fetched items as UniversalDocuments.
     *
     * @param syncJob The current sync job context
     * @return List of UniversalDocument items ingested
     */
    List<UniversalDocument> sync(ConnectorSyncJob syncJob);

    /**
     * Searches the remote enterprise system directly via external search API.
     *
     * @param query Search query text
     * @param limit Maximum results
     * @return List of FederatedSearchResult objects
     */
    List<FederatedSearchResult> searchRemote(String query, int limit);

    /**
     * Tests connectivity and credentials for health checks.
     *
     * @return Health indicator
     */
    ConnectorHealth testHealth();

    /**
     * Returns technical metadata of the connector adapter.
     */
    ConnectorMetadata getMetadata();

    /**
     * Returns real-time execution statistics for the connector.
     */
    ConnectorStatistics getStatistics();

    /**
     * Returns current lifecycle state of the connector.
     */
    ConnectorState getState();
}
