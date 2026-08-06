package com.atlas.reasoning.service;

import com.atlas.common.dto.reasoning.CritiqueRequest;
import com.atlas.domain.reasoning.CritiqueRecord;

public interface CriticEngine {
    CritiqueRecord critiqueExecution(String tenantId, CritiqueRequest request);
}
