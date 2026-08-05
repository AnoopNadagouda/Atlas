package com.atlas.memory.repository;

import com.atlas.memory.entity.MemoryEmbeddingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemoryEmbeddingRepository extends JpaRepository<MemoryEmbeddingEntity, String> {
}
