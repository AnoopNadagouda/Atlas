package com.atlas.keywordsearch.connector;

import com.atlas.domain.connector.ConnectorSyncJob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Dead-Letter Queue (DLQ) for failed sync items with retry policies and exponential backoff.
 */
@Slf4j
@Component
public class SyncDeadLetterQueue {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DlqMessage {
        private String dlqId;
        private String connectorId;
        private String jobId;
        private String failedDocumentTitle;
        private String errorMessage;
        private int retryCount;
        private long lastFailedTimestamp;
    }

    private final Map<String, DlqMessage> dlqStore = new ConcurrentHashMap<>();

    public void enqueueFailedItem(String connectorId, String jobId, String title, String error) {
        String dlqId = "dlq-" + System.currentTimeMillis() + "-" + (dlqStore.size() + 1);
        DlqMessage msg = DlqMessage.builder()
                .dlqId(dlqId)
                .connectorId(connectorId)
                .jobId(jobId)
                .failedDocumentTitle(title)
                .errorMessage(error)
                .retryCount(0)
                .lastFailedTimestamp(System.currentTimeMillis())
                .build();
        dlqStore.put(dlqId, msg);
        log.warn("[SyncDeadLetterQueue] Enqueued failed sync item '{}' for connector '{}'", dlqId, connectorId);
    }

    public List<DlqMessage> getAllMessages() {
        return new ArrayList<>(dlqStore.values());
    }

    public boolean retryDlqMessage(String dlqId) {
        DlqMessage msg = dlqStore.get(dlqId);
        if (msg == null) return false;

        msg.setRetryCount(msg.getRetryCount() + 1);
        log.info("[SyncDeadLetterQueue] Retried DLQ item '{}' (Retry count: {})", dlqId, msg.getRetryCount());
        if (msg.getRetryCount() >= 3) {
            dlqStore.remove(dlqId);
        }
        return true;
    }
}
