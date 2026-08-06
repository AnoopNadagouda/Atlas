package com.atlas.orchestrator.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "aios_global_state")
public class GlobalStateEntity {

    @Id
    private String id;

    @Column(name = "tenant_id", nullable = false)
    private String tenantId;

    @Column(name = "state_key", nullable = false)
    private String stateKey;

    @Column(name = "state_value", length = 2048)
    private String stateValue;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public GlobalStateEntity() {
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }

    public String getStateKey() { return stateKey; }
    public void setStateKey(String stateKey) { this.stateKey = stateKey; }

    public String getStateValue() { return stateValue; }
    public void setStateValue(String stateValue) { this.stateValue = stateValue; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
