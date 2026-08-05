package com.atlas.domain.connector;

/**
 * Connector lifecycle state enum.
 */
public enum ConnectorState {
    REGISTERED,
    CONNECTING,
    CONNECTED,
    SYNCING,
    FAILED,
    DISABLED
}
