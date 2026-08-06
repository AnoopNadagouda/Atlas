package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.GlobalState;
import com.atlas.orchestrator.entity.GlobalStateEntity;
import com.atlas.orchestrator.repository.GlobalStateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class GlobalStateManagerImpl implements GlobalStateManager {

    private static final Logger log = LoggerFactory.getLogger(GlobalStateManagerImpl.class);

    private final GlobalStateRepository repository;

    public GlobalStateManagerImpl(GlobalStateRepository repository) {
        this.repository = repository;
    }

    @Override
    public GlobalState setState(String tenantId, String key, String value) {
        log.info("[GlobalStateManager] Setting global state key '{}' for tenant '{}'", key, tenantId);
        GlobalStateEntity entity = repository.findByTenantIdAndStateKey(tenantId, key)
            .orElseGet(() -> {
                GlobalStateEntity e = new GlobalStateEntity();
                e.setId(UUID.randomUUID().toString());
                e.setTenantId(tenantId);
                e.setStateKey(key);
                return e;
            });
        entity.setStateValue(value);
        entity.setUpdatedAt(Instant.now());

        repository.save(entity);

        GlobalState gs = new GlobalState();
        gs.setId(entity.getId());
        gs.setTenantId(entity.getTenantId());
        gs.setStateKey(entity.getStateKey());
        gs.setStateValue(entity.getStateValue());
        gs.setUpdatedAt(entity.getUpdatedAt());
        return gs;
    }

    @Override
    public List<GlobalState> getGlobalState(String tenantId) {
        return repository.findByTenantId(tenantId).stream().map(e -> {
            GlobalState gs = new GlobalState();
            gs.setId(e.getId());
            gs.setTenantId(e.getTenantId());
            gs.setStateKey(e.getStateKey());
            gs.setStateValue(e.getStateValue());
            gs.setUpdatedAt(e.getUpdatedAt());
            return gs;
        }).toList();
    }
}
