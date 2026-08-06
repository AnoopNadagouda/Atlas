package com.atlas.memory.service;

import com.atlas.common.dto.memory.ContextRestorationRequest;
import com.atlas.common.dto.memory.ContextRestorationResponse;

public interface MemorySyncService {
    ContextRestorationResponse restoreContext(String tenantId, ContextRestorationRequest request);
    void syncSessionState(String tenantId, String sessionId);
}
