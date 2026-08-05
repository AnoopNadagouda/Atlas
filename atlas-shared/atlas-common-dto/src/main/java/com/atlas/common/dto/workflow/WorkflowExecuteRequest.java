package com.atlas.common.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowExecuteRequest {
    private String definitionId;
    private Map<String, Object> inputValues;
}
