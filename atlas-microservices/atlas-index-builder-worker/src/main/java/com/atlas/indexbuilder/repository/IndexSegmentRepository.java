package com.atlas.indexbuilder.repository;

import com.atlas.indexbuilder.entity.IndexSegmentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexSegmentRepository extends JpaRepository<IndexSegmentEntity, String> {
    Page<IndexSegmentEntity> findAll(Pageable pageable);
}
