package com.atlas.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

public final class RetryUtils {

    private static final Logger log = LoggerFactory.getLogger(RetryUtils.class);

    private RetryUtils() {}

    public static <T> T executeWithRetry(Supplier<T> action, int maxRetries, long initialDelayMs) {
        int attempts = 0;
        long delay = initialDelayMs;

        while (attempts < maxRetries) {
            try {
                return action.get();
            } catch (Exception e) {
                attempts++;
                if (attempts >= maxRetries) {
                    log.error("Action failed after {} max attempts", maxRetries, e);
                    throw e;
                }
                log.warn("Attempt {}/{} failed: {}. Retrying in {} ms...", attempts, maxRetries, e.getMessage(), delay);
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new IllegalStateException("Interrupted during retry backoff", ie);
                }
                delay *= 2; // Exponential backoff
            }
        }
        throw new IllegalStateException("Exhausted retries");
    }
}
