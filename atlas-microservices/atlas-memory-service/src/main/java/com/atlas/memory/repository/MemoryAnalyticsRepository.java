package com.atlas.memory.repository;

import com.atlas.memory.entity.MemoryAnalyticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemoryAnalyticsRepository extends JpaRepository<MemoryAnalyticsEntity, String> {
    Optional<MemoryAnalyticsEntity> findTopByTenantIdOrderByCalculatedAtDesc(String tenantId);
}
