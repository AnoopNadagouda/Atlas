package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkflowStepRepository extends JpaRepository<WorkflowStepEntity, String> {
    List<WorkflowStepEntity> findByInstanceId(String instanceId);
    Optional<WorkflowStepEntity> findByInstanceIdAndStepId(String instanceId, String stepId);
}
