package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ThoughtNodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThoughtNodeRepository extends JpaRepository<ThoughtNodeEntity, String> {
    List<ThoughtNodeEntity> findBySessionId(String sessionId);
}
