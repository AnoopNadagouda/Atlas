package com.atlas.common.dto.workflow;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WorkflowTemplateResponse {
    private String id;
    private String name;
    private String category;
    private String description;
    private String definitionJson;
    private List<String> tags;
}
