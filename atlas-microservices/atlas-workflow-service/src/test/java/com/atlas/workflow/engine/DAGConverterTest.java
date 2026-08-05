package com.atlas.workflow.engine;

import com.atlas.domain.workflow.WorkflowDefinition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DAGConverterTest {

    private final DAGConverter dagConverter = new DAGConverter();

    @Test
    void testParseJsonDefinitionSuccess() {
        String json = """
                {
                  "name": "Test Search & Crawl DAG",
                  "description": "Orchestrate crawling and search indexing",
                  "version": "1.0.0",
                  "steps": [
                    {
                      "stepId": "crawl-step",
                      "name": "Crawl Target Domain",
                      "type": "CONNECTOR_EXECUTION"
                    },
                    {
                      "stepId": "parse-step",
                      "name": "Parse HTML Content",
                      "type": "SEQUENTIAL",
                      "dependsOn": ["crawl-step"]
                    }
                  ]
                }
                """;

        WorkflowDefinition def = dagConverter.parseDefinition(json, false);
        assertNotNull(def);
        assertEquals("Test Search & Crawl DAG", def.getName());
        assertEquals(2, def.getSteps().size());
        assertEquals("crawl-step", def.getSteps().get(0).getStepId());
        assertEquals("parse-step", def.getSteps().get(1).getStepId());
    }

    @Test
    void testDuplicateStepIdThrowsException() {
        String json = """
                {
                  "name": "Bad DAG",
                  "steps": [
                    { "stepId": "step-1", "type": "SEQUENTIAL" },
                    { "stepId": "step-1", "type": "SEQUENTIAL" }
                  ]
                }
                """;

        assertThrows(IllegalArgumentException.class, () -> dagConverter.parseDefinition(json, false));
    }
}
