package com.atlas.workflow.engine;

import com.atlas.common.utils.JsonUtils;
import com.atlas.domain.workflow.WorkflowCheckpoint;
import com.atlas.domain.workflow.WorkflowContext;
import com.atlas.domain.workflow.WorkflowState;
import com.atlas.workflow.entity.WorkflowCheckpointEntity;
import com.atlas.workflow.repository.WorkflowCheckpointRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * Manager responsible for taking, persisting, and restoring Workflow Checkpoints.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CheckpointManager {

    private final WorkflowCheckpointRepository checkpointRepository;

    public WorkflowCheckpoint saveCheckpoint(WorkflowContext context, String stepId, WorkflowState state) {
        String checkpointId = UUID.randomUUID().toString();
        String jsonVars = JsonUtils.toJson(context.getAllVariables());

        WorkflowCheckpointEntity entity = WorkflowCheckpointEntity.builder()
                .checkpointId(checkpointId)
                .instanceId(context.getInstanceId())
                .stepId(stepId)
                .state(state.name())
                .contextVariablesJson(jsonVars)
                .createdAt(Instant.now())
                .build();

        checkpointRepository.save(entity);
        log.info("[CheckpointManager] Saved checkpoint '{}' for instance '{}' at step '{}'", checkpointId, context.getInstanceId(), stepId);

        return WorkflowCheckpoint.builder()
                .checkpointId(checkpointId)
                .instanceId(context.getInstanceId())
                .stepId(stepId)
                .state(state)
                .contextVariables(context.getAllVariables())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public WorkflowCheckpoint getLatestCheckpoint(String instanceId) {
        List<WorkflowCheckpointEntity> list = checkpointRepository.findByInstanceIdOrderByCreatedAtDesc(instanceId);
        if (list.isEmpty()) {
            return null;
        }
        WorkflowCheckpointEntity entity = list.get(0);
        return WorkflowCheckpoint.builder()
                .checkpointId(entity.getCheckpointId())
                .instanceId(entity.getInstanceId())
                .stepId(entity.getStepId())
                .state(WorkflowState.valueOf(entity.getState()))
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
