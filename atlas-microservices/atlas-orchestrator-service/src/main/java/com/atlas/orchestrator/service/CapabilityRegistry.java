package com.atlas.orchestrator.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CapabilityRegistry implements ServiceDiscoveryManager {

    private static final Logger log = LoggerFactory.getLogger(CapabilityRegistry.class);

    private final Map<String, String> services = Map.of(
        "atlas-agent-service", "http://localhost:8086",
        "atlas-workflow-service", "http://localhost:8087",
        "atlas-memory-service", "http://localhost:8088",
        "atlas-reasoning-service", "http://localhost:8089",
        "atlas-orchestrator-service", "http://localhost:8090"
    );

    @Override
    public Map<String, String> getRegisteredServices() {
        return services;
    }

    @Override
    public List<String> getCapabilities(String serviceName) {
        log.info("[CapabilityRegistry] Querying capabilities for microservice '{}'", serviceName);
        return List.of("AGENT_EXECUTION", "WORKFLOW_ORCHESTRATION", "MEMORY_STORAGE", "REASONING_ENGINE", "AIOS_ORCHESTRATION");
    }
}
