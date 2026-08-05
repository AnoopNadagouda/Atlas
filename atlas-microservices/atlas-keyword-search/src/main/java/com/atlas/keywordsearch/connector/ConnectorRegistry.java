package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.Connector;
import com.atlas.domain.connector.ConnectorMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Central registry managing all registered Connector SPI implementations.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectorRegistry {

    private final List<Connector> injectedConnectors;
    private final Map<String, Connector> connectorMap = new ConcurrentHashMap<>();

    public void registerInjectedConnectors() {
        if (injectedConnectors != null) {
            for (Connector connector : injectedConnectors) {
                if (connector.getMetadata() != null && connector.getMetadata().getConnectorId() != null) {
                    connectorMap.put(connector.getMetadata().getConnectorId(), connector);
                    log.info("[ConnectorRegistry] Registered connector adapter: '{}' ({})", connector.getMetadata().getName(), connector.getMetadata().getConnectorId());
                }
            }
        }
    }

    public Connector getConnector(String connectorId) {
        if (connectorMap.isEmpty()) {
            registerInjectedConnectors();
        }
        return connectorMap.get(connectorId);
    }

    public List<Connector> getAllConnectors() {
        if (connectorMap.isEmpty()) {
            registerInjectedConnectors();
        }
        return new ArrayList<>(connectorMap.values());
    }

    public List<ConnectorMetadata> getAllMetadata() {
        List<ConnectorMetadata> list = new ArrayList<>();
        for (Connector c : getAllConnectors()) {
            if (c.getMetadata() != null) {
                list.add(c.getMetadata());
            }
        }
        return list;
    }
}
