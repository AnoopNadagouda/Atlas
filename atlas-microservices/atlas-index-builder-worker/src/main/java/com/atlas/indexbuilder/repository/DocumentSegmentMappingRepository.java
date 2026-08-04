package com.atlas.indexbuilder.repository;

import com.atlas.indexbuilder.entity.DocumentSegmentMappingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentSegmentMappingRepository extends JpaRepository<DocumentSegmentMappingEntity, String> {
    List<DocumentSegmentMappingEntity> findByDocId(String docId);
}
