package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ReasoningTraceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReasoningTraceRepository extends JpaRepository<ReasoningTraceEntity, String> {
    Optional<ReasoningTraceEntity> findBySessionId(String sessionId);
}
