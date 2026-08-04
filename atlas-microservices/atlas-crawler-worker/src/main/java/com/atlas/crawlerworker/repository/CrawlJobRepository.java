package com.atlas.crawlerworker.repository;

import com.atlas.crawlerworker.entity.CrawlJobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrawlJobRepository extends JpaRepository<CrawlJobEntity, String> {
}
