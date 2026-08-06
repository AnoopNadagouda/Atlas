package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ThoughtEdgeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ThoughtEdgeRepository extends JpaRepository<ThoughtEdgeEntity, String> {
    List<ThoughtEdgeEntity> findBySourceNodeId(String sourceNodeId);
    List<ThoughtEdgeEntity> findByTargetNodeId(String targetNodeId);
}
