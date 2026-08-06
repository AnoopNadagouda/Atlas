package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.ClusterStateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClusterStateRepository extends JpaRepository<ClusterStateEntity, String> {
}
