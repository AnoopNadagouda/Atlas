package com.atlas.common.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowApprovalRequest {
    private String instanceId;
    private String stepId;
    private boolean approved;
    private String approverId;
    private String comments;
}
