package com.atlas.orchestrator.service;

import java.util.List;
import java.util.Map;

public interface ServiceDiscoveryManager {
    Map<String, String> getRegisteredServices();
    List<String> getCapabilities(String serviceName);
}
