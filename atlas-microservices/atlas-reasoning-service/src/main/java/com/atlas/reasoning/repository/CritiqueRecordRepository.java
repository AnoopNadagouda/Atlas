package com.atlas.reasoning.repository;

import com.atlas.reasoning.entity.CritiqueRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CritiqueRecordRepository extends JpaRepository<CritiqueRecordEntity, String> {
    List<CritiqueRecordEntity> findByTenantId(String tenantId);
    List<CritiqueRecordEntity> findBySessionId(String sessionId);
}
