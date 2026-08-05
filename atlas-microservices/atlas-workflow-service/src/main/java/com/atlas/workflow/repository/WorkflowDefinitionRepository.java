package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowDefinitionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowDefinitionRepository extends JpaRepository<WorkflowDefinitionEntity, String> {
    List<WorkflowDefinitionEntity> findByState(String state);
}
