package com.atlas.indexbuilder.repository;

import com.atlas.indexbuilder.entity.IndexStatisticsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndexStatisticsRepository extends JpaRepository<IndexStatisticsEntity, String> {
}
