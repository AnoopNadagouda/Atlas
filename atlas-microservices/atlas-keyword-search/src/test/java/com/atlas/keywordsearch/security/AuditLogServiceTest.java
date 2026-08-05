package com.atlas.keywordsearch.security;

import com.atlas.domain.security.AuditLogEntry;
import com.atlas.domain.security.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AuditLogServiceTest {

    private AuditLogService auditLogService;

    @BeforeEach
    void setUp() {
        auditLogService = new AuditLogService();
        auditLogService.initSeedAuditLogs();
    }

    @Test
    void testAuditLogRecording() {
        auditLogService.recordAudit(AuditLogEntry.builder()
                .id(UUID.randomUUID().toString())
                .timestamp(Instant.now())
                .userId("test-user")
                .role(UserRole.SEARCH_USER)
                .action("SEARCH_QUERY")
                .resource("/api/v1/search")
                .clientIp("127.0.0.1")
                .status("SUCCESS")
                .details("Executed search query")
                .build());

        List<AuditLogEntry> logs = auditLogService.getAuditLogs();
        assertTrue(logs.size() >= 3);
    }
}
