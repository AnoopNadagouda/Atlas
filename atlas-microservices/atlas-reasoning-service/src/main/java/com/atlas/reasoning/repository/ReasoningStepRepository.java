package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ReasoningStepEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReasoningStepRepository extends JpaRepository<ReasoningStepEntity, String> {
    List<ReasoningStepEntity> findBySessionId(String sessionId);
}
