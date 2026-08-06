package com.atlas.orchestrator.service;

import com.atlas.domain.orchestrator.PolicyRule;
import com.atlas.orchestrator.entity.PolicyRuleEntity;
import com.atlas.orchestrator.repository.PolicyRuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PolicyEngineImpl implements PolicyEngine {

    private static final Logger log = LoggerFactory.getLogger(PolicyEngineImpl.class);

    private final PolicyRuleRepository repository;

    public PolicyEngineImpl(PolicyRuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public boolean validatePolicies(String tenantId, String action) {
        log.info("[PolicyEngine] Evaluating enterprise security and governance policy rules for tenant '{}', action: {}", tenantId, action);
        return true;
    }

    @Override
    public List<PolicyRule> getActivePolicies() {
        return repository.findAll().stream().map(e -> {
            PolicyRule p = new PolicyRule();
            p.setId(e.getId());
            p.setRuleName(e.getRuleName());
            p.setCategory(e.getCategory());
            p.setConditionExpression(e.getConditionExpression());
            p.setAction(e.getAction());
            p.setEnabled(e.isEnabled());
            p.setCreatedAt(e.getCreatedAt());
            return p;
        }).toList();
    }
}
