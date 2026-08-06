package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.PolicyRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PolicyRuleRepository extends JpaRepository<PolicyRuleEntity, String> {
    List<PolicyRuleEntity> findByCategory(String category);
}
