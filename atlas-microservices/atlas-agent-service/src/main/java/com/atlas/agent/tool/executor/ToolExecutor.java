package com.atlas.agent.tool.executor;

import com.atlas.domain.agent.tool.ToolExecutionRequest;
import com.atlas.domain.agent.tool.ToolExecutionResult;

public interface ToolExecutor {
    ToolExecutionResult executeTool(ToolExecutionRequest request);
}
