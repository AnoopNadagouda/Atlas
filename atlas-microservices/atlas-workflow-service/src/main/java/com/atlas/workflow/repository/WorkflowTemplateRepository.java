package com.atlas.workflow.repository;

import com.atlas.workflow.entity.WorkflowTemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkflowTemplateRepository extends JpaRepository<WorkflowTemplateEntity, String> {
    List<WorkflowTemplateEntity> findByCategory(String category);
}
