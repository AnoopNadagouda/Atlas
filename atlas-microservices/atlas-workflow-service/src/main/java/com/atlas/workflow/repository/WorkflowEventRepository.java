package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowEventRepository extends JpaRepository<WorkflowEventEntity, String> {
    List<WorkflowEventEntity> findByInstanceIdOrderByTimestampAsc(String instanceId);
}
