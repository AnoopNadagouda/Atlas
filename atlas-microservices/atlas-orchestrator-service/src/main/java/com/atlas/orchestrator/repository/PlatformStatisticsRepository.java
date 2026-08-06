package com.atlas.orchestrator.repository;

import com.atlas.orchestrator.entity.PlatformStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlatformStatisticsRepository extends JpaRepository<PlatformStatisticsEntity, String> {
    Optional<PlatformStatisticsEntity> findByTenantId(String tenantId);
}
