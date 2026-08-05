package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowInstanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstanceEntity, String> {
    List<WorkflowInstanceEntity> findByDefinitionId(String definitionId);
    List<WorkflowInstanceEntity> findByState(String state);
}
