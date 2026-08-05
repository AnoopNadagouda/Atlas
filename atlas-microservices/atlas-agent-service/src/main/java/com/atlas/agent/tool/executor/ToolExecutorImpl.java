package com.atlas.agent.tool.executor;

import com.atlas.agent.tool.registry.ToolRegistry;
import com.atlas.agent.tool.validation.ToolSchemaValidator;
import com.atlas.domain.agent.tool.*;
import com.atlas.domain.agent.tool.exception.ToolValidationException;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.*;

@Slf4j
@Service
public class ToolExecutorImpl implements ToolExecutor {

    private final ToolRegistry toolRegistry;
    private final ToolSchemaValidator schemaValidator;
    private final MeterRegistry meterRegistry;
    private final ExecutorService executorService;

    public ToolExecutorImpl(ToolRegistry toolRegistry, ToolSchemaValidator schemaValidator, MeterRegistry meterRegistry) {
        this.toolRegistry = toolRegistry;
        this.schemaValidator = schemaValidator;
        this.meterRegistry = meterRegistry;
        this.executorService = Executors.newVirtualThreadPerTaskExecutor();
    }

    @Override
    public ToolExecutionResult executeTool(ToolExecutionRequest request) {
        Instant startTime = Instant.now();
        String executionId = request.getExecutionId() != null ? request.getExecutionId() : UUID.randomUUID().toString();
        String toolId = request.getToolId();

        log.info("Executing Tool Request ID: [{}] for Tool ID: [{}]", executionId, toolId);

        // 1. Tool Lookup
        Optional<AgentTool> toolOpt = toolRegistry.getTool(toolId);
        if (toolOpt.isEmpty()) {
            return buildResult(toolId, executionId, ToolExecutionStatus.EXECUTION_ERROR, null, "Tool not found: " + toolId, startTime);
        }

        AgentTool tool = toolOpt.get();
        ToolMetadata metadata = tool.getMetadata();

        // 2. Check if Tool is Enabled
        if (!toolRegistry.isToolEnabled(toolId)) {
            return buildResult(toolId, executionId, ToolExecutionStatus.EXECUTION_ERROR, null, "Tool is currently disabled: " + toolId, startTime);
        }

        // 3. Security Permission Verification (Step 8)
        if (metadata.getPermissions() != null && !metadata.getPermissions().isEmpty()) {
            if (request.getCallerPermissions() == null || !request.getCallerPermissions().containsAll(metadata.getPermissions())) {
                log.warn("Permission denied for tool [{}] execution. Required: {}, Caller has: {}", toolId, metadata.getPermissions(), request.getCallerPermissions());
                return buildResult(toolId, executionId, ToolExecutionStatus.PERMISSION_DENIED, null, "Permission denied. Required: " + metadata.getPermissions(), startTime);
            }
        }

        // 4. Schema Validation (Step 5)
        try {
            schemaValidator.validate(metadata, request.getParameters());
        } catch (ToolValidationException e) {
            log.warn("Validation failed for tool [{}]: {}", toolId, e.getValidationErrors());
            return buildResult(toolId, executionId, ToolExecutionStatus.VALIDATION_ERROR, null, "Validation errors: " + e.getValidationErrors(), startTime);
        }

        // 5. Execution with Timeout Handling
        long timeoutMs = request.getTimeoutMs() > 0 ? request.getTimeoutMs() : metadata.getTimeoutMs();

        CompletableFuture<Map<String, Object>> future = CompletableFuture.supplyAsync(() -> {
            try {
                return tool.execute(request.getParameters());
            } catch (Exception e) {
                throw new CompletionException(e);
            }
        }, executorService);

        try {
            Map<String, Object> output = future.get(timeoutMs, TimeUnit.MILLISECONDS);
            ToolExecutionResult result = buildResult(toolId, executionId, ToolExecutionStatus.SUCCESS, output, null, startTime);
            recordMetrics(toolId, ToolExecutionStatus.SUCCESS, result.getLatencyMs());
            return result;

        } catch (TimeoutException e) {
            future.cancel(true);
            log.error("Tool [{}] execution timed out after {} ms", toolId, timeoutMs);
            ToolExecutionResult result = buildResult(toolId, executionId, ToolExecutionStatus.TIMEOUT, null, "Execution timed out after " + timeoutMs + " ms", startTime);
            recordMetrics(toolId, ToolExecutionStatus.TIMEOUT, result.getLatencyMs());
            return result;

        } catch (Exception e) {
            Throwable cause = e.getCause() != null ? e.getCause() : e;
            log.error("Tool [{}] execution error", toolId, cause);
            ToolExecutionResult result = buildResult(toolId, executionId, ToolExecutionStatus.EXECUTION_ERROR, null, "Execution error: " + cause.getMessage(), startTime);
            recordMetrics(toolId, ToolExecutionStatus.EXECUTION_ERROR, result.getLatencyMs());
            return result;
        }
    }

    private ToolExecutionResult buildResult(String toolId, String executionId, ToolExecutionStatus status, Map<String, Object> output, String errorMessage, Instant startTime) {
        Instant endTime = Instant.now();
        long latencyMs = Duration.between(startTime, endTime).toMillis();
        return ToolExecutionResult.builder()
                .toolId(toolId)
                .executionId(executionId)
                .status(status)
                .output(output)
                .errorMessage(errorMessage)
                .startTime(startTime)
                .endTime(endTime)
                .latencyMs(latencyMs)
                .build();
    }

    private void recordMetrics(String toolId, ToolExecutionStatus status, long latencyMs) {
        if (meterRegistry != null) {
            meterRegistry.counter("atlas.agent.tool.executions.total", "tool_id", toolId, "status", status.name()).increment();
            Timer.builder("atlas.agent.tool.latency")
                    .tag("tool_id", toolId)
                    .register(meterRegistry)
                    .record(Duration.ofMillis(latencyMs));
        }
    }
}
