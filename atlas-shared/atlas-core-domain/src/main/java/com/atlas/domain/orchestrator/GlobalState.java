package com.atlas.domain.orchestrator;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class GlobalState {
    private String id;
    private String tenantId;
    private String stateKey;
    private String stateValue;
    private Map<String, String> metadata = new HashMap<>();
    private Instant updatedAt;

    public GlobalState() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getStateKey() { return stateKey; }
    public void setStateKey(String stateKey) { this.stateKey = stateKey; }

    public String getStateValue() { return stateValue; }
    public void setStateValue(String stateValue) { this.stateValue = stateValue; }

    public Map<String, String> getMetadata() { return metadata; }
    public void setMetadata(Map<String, String> metadata) { this.metadata = metadata; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
