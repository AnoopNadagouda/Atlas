package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowCheckpointEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowCheckpointRepository extends JpaRepository<WorkflowCheckpointEntity, String> {
    List<WorkflowCheckpointEntity> findByInstanceIdOrderByCreatedAtDesc(String instanceId);
}
