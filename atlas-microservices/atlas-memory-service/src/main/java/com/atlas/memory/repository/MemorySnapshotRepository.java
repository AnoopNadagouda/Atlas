package com.atlas.memory.repository;

import com.atlas.memory.entity.MemorySnapshotEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemorySnapshotRepository extends JpaRepository<MemorySnapshotEntity, String> {
    List<MemorySnapshotEntity> findByTenantId(String tenantId);
}
