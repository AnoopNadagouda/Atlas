package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.ReflectionRequest;
import com.atlas.domain.reasoning.ReflectionRecord;

public interface SelfReflectionEngine {
    ReflectionRecord reflectOnExecution(String tenantId, ReflectionRequest request);
}
