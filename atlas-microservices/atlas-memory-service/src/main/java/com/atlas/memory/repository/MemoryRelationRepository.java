package com.atlas.memory.repository;

import com.atlas.memory.entity.MemoryRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemoryRelationRepository extends JpaRepository<MemoryRelationEntity, String> {
    List<MemoryRelationEntity> findBySourceMemoryId(String sourceMemoryId);
    List<MemoryRelationEntity> findByTargetMemoryId(String targetMemoryId);
}
