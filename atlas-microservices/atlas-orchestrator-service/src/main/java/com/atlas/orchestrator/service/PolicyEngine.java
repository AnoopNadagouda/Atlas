package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.PolicyRule;
import java.util.List;

public interface PolicyEngine {
    boolean validatePolicies(String tenantId, String action);
    List<PolicyRule> getActivePolicies();
}
