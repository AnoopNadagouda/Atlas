package com.atlas.keywordsearch.security;

import com.atlas.domain.security.AuditLogEntry;
import com.atlas.domain.security.UserRole;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@Service
public class AuditLogService {

    private final Queue<AuditLogEntry> auditLogs = new ConcurrentLinkedQueue<>();

    @PostConstruct
    public void initSeedAuditLogs() {
        log.info("Initializing Audit Log Service with seed enterprise events...");
        recordAudit(AuditLogEntry.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .userId("admin-user")
                .role(UserRole.ADMIN)
                .action("CLUSTER_FAILOVER_PROMOTE")
                .resource("/api/v4/cluster/promote")
                .clientIp("127.0.0.1")
                .status("SUCCESS")
                .details("Promoted search-node-2 replica to primary for shard-0")
                .build());

        recordAudit(AuditLogEntry.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .userId("operator-user")
                .role(UserRole.OPERATOR)
                .action("INDEX_SEGMENT_MERGE_START")
                .resource("/api/v5/index/merge/start")
                .clientIp("127.0.0.1")
                .status("SUCCESS")
                .details("Triggered background compaction merge for shard-0")
                .build());
    }

    public void recordAudit(AuditLogEntry entry) {
        if (entry == null) return;
        auditLogs.add(entry);
        log.info("[AuditLogService] Recorded Audit Action: '{}' by user '{}' ({})",
                entry.getAction(), entry.getUserId(), entry.getStatus());
    }

    public List<AuditLogEntry> getAuditLogs() {
        return new ArrayList<>(auditLogs);
    }
}
