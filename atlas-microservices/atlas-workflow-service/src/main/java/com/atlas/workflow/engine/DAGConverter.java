package com.atlas.workflow.engine;

import com.atlas.common.utils.JsonUtils;
import com.atlas.domain.workflow.StepType;
import com.atlas.domain.workflow.WorkflowDefinition;
import com.atlas.domain.workflow.WorkflowStep;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Converter supporting JSON and YAML workflow definition formats, converting them into executable DAGs.
 */
@Component
public class DAGConverter {

    private final ObjectMapper jsonMapper = new ObjectMapper();
    private final ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

    public WorkflowDefinition parseDefinition(String content, boolean isYaml) {
        try {
            ObjectMapper mapper = isYaml ? yamlMapper : jsonMapper;
            Map<String, Object> map = mapper.readValue(content, new TypeReference<Map<String, Object>>() {});
            
            String id = (String) map.getOrDefault("id", UUID.randomUUID().toString());
            String name = (String) map.getOrDefault("name", "Untitled Workflow");
            String description = (String) map.getOrDefault("description", "");
            String version = (String) map.getOrDefault("version", "1.0.0");
            String cronSchedule = (String) map.get("cronSchedule");

            List<Map<String, Object>> stepMaps = (List<Map<String, Object>>) map.getOrDefault("steps", Collections.emptyList());
            List<WorkflowStep> steps = new ArrayList<>();

            for (Map<String, Object> s : stepMaps) {
                String stepId = (String) s.get("stepId");
                String stepName = (String) s.getOrDefault("name", stepId);
                String stepDesc = (String) s.getOrDefault("description", "");
                String typeStr = (String) s.getOrDefault("type", "SEQUENTIAL");
                StepType type = StepType.valueOf(typeStr.toUpperCase());

                List<String> dependsOn = (List<String>) s.getOrDefault("dependsOn", Collections.emptyList());
                Map<String, Object> params = (Map<String, Object>) s.getOrDefault("parameters", Collections.emptyMap());
                int maxRetries = ((Number) s.getOrDefault("maxRetries", 3)).intValue();
                long timeoutMs = ((Number) s.getOrDefault("timeoutMs", 30000L)).longValue();
                String condExpr = (String) s.get("conditionExpression");
                String agentId = (String) s.get("agentId");
                String connectorId = (String) s.get("connectorId");
                String approvalRole = (String) s.get("approvalRole");

                steps.add(WorkflowStep.builder()
                        .stepId(stepId)
                        .name(stepName)
                        .description(stepDesc)
                        .type(type)
                        .dependsOn(dependsOn)
                        .parameters(params)
                        .maxRetries(maxRetries)
                        .timeoutMs(timeoutMs)
                        .conditionExpression(condExpr)
                        .agentId(agentId)
                        .connectorId(connectorId)
                        .approvalRole(approvalRole)
                        .build());
            }

            validateDAG(steps);

            return WorkflowDefinition.builder()
                    .id(id)
                    .name(name)
                    .description(description)
                    .version(version)
                    .steps(steps)
                    .cronSchedule(cronSchedule)
                    .build();

        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse workflow definition: " + e.getMessage(), e);
        }
    }

    public void validateDAG(List<WorkflowStep> steps) {
        Set<String> stepIds = new HashSet<>();
        for (WorkflowStep step : steps) {
            if (!stepIds.add(step.getStepId())) {
                throw new IllegalArgumentException("Duplicate stepId in DAG: " + step.getStepId());
            }
        }

        for (WorkflowStep step : steps) {
            if (step.getDependsOn() != null) {
                for (String dep : step.getDependsOn()) {
                    if (!stepIds.contains(dep)) {
                        throw new IllegalArgumentException("Step [" + step.getStepId() + "] depends on non-existent step [" + dep + "]");
                    }
                }
            }
        }
    }
}
