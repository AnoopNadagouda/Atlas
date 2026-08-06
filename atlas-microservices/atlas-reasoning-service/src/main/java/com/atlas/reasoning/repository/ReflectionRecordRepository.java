package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.ReflectionRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReflectionRecordRepository extends JpaRepository<ReflectionRecordEntity, String> {
    List<ReflectionRecordEntity> findByTenantId(String tenantId);
    List<ReflectionRecordEntity> findBySessionId(String sessionId);
}
