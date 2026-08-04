package com.atlas.crawlerworker.repository;

import com.atlas.crawlerworker.entity.CrawlUrlEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CrawlUrlRepository extends JpaRepository<CrawlUrlEntity, String> {
    Page<CrawlUrlEntity> findByJobId(String jobId, Pageable pageable);
    boolean existsByJobIdAndNormalizedUrl(String jobId, String normalizedUrl);
    long countByJobIdAndStatus(String jobId, String status);
}
