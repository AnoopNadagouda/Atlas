package com.atlas.workflow.engine;

import com.atlas.domain.workflow.StepStatus;
import com.atlas.domain.workflow.WorkflowState;
import com.atlas.workflow.entity.WorkflowInstanceEntity;
import com.atlas.workflow.entity.WorkflowStepEntity;
import com.atlas.workflow.repository.WorkflowInstanceRepository;
import com.atlas.workflow.repository.WorkflowStepRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

/**
 * Manager handling Human Approval step gates in workflow instances.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class HumanApprovalManager {

    private final WorkflowInstanceRepository instanceRepository;
    private final WorkflowStepRepository stepRepository;

    @Transactional
    public boolean processApproval(String instanceId, String stepId, boolean approved, String approverId, String comments) {
        log.info("[ApprovalManager] Processing approval for Instance: {}, Step: {}, Approved: {}", instanceId, stepId, approved);

        Optional<WorkflowInstanceEntity> instOpt = instanceRepository.findById(instanceId);
        if (instOpt.isEmpty()) {
            throw new IllegalArgumentException("Workflow instance not found: " + instanceId);
        }

        WorkflowInstanceEntity instance = instOpt.get();
        Optional<WorkflowStepEntity> stepOpt = stepRepository.findByInstanceIdAndStepId(instanceId, stepId);

        if (stepOpt.isPresent()) {
            WorkflowStepEntity step = stepOpt.get();
            step.setEndTime(Instant.now());
            step.setDurationMs(step.getEndTime().toEpochMilli() - (step.getStartTime() != null ? step.getStartTime().toEpochMilli() : step.getEndTime().toEpochMilli()));
            
            if (approved) {
                step.setStatus(StepStatus.COMPLETED.name());
                step.setResultJson("{\"approved\": true, \"approverId\": \"" + approverId + "\", \"comments\": \"" + comments + "\"}");
                instance.setState(WorkflowState.RUNNING.name());
            } else {
                step.setStatus(StepStatus.FAILED.name());
                step.setErrorMessage("Rejected by approver: " + approverId + ". Comments: " + comments);
                instance.setState(WorkflowState.FAILED.name());
                instance.setErrorMessage("Workflow rejected at step: " + stepId);
            }
            stepRepository.save(step);
            instanceRepository.save(instance);
            return approved;
        }
        return false;
    }
}
