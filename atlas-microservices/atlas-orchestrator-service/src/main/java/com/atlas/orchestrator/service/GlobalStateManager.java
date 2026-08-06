package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.GlobalState;
import java.util.List;

public interface GlobalStateManager {
    GlobalState setState(String tenantId, String key, String value);
    List<GlobalState> getGlobalState(String tenantId);
}
