package com.atlas.agent.domain;

import com.atlas.domain.agent.*;
import com.atlas.domain.agent.exception.AgentExecutionException;
import com.atlas.domain.agent.exception.AgentInitializationException;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.Instant;
import java.util.Collections;

@Slf4j
public class CoreAgentImpl implements CoreAgent {

    private final String agentId;
    private AgentContext context;
    private final AgentLifecycleListener listener;

    public CoreAgentImpl(String agentId, AgentLifecycleListener listener) {
        this.agentId = agentId;
        this.listener = listener;
    }

    @Override
    public String getAgentId() {
        return agentId;
    }

    @Override
    public AgentContext getContext() {
        return context;
    }

    @Override
    public AgentState getState() {
        return context != null ? context.getState() : AgentState.CREATED;
    }

    @Override
    public synchronized void initialize(AgentExecutionConfig config) {
        if (config == null) {
            throw new AgentInitializationException("Agent execution config cannot be null");
        }
        String sessionId = config.getSessionId() != null ? config.getSessionId() : "session-" + System.currentTimeMillis();
        this.context = new AgentContext(sessionId, agentId, config);

        AgentState oldState = context.getState();
        if (context.transitionState(AgentState.INITIALIZING)) {
            notifyStateTransition(oldState, AgentState.INITIALIZING);
        }

        context.log("Agent initialized with config: " + config.getAgentId());
        
        oldState = context.getState();
        if (context.transitionState(AgentState.READY)) {
            notifyStateTransition(oldState, AgentState.READY);
        }
    }

    @Override
    public AgentResult execute() {
        if (context == null || context.getState() != AgentState.READY) {
            throw new AgentExecutionException("Agent is not in READY state for execution", agentId, context != null ? context.getSessionId() : null);
        }

        Instant startTime = Instant.now();
        AgentState oldState = context.getState();
        if (context.transitionState(AgentState.RUNNING)) {
            notifyStateTransition(oldState, AgentState.RUNNING);
        }

        context.log("Executing agent loop for prompt: " + context.getConfig().getUserPrompt());

        try {
            // Execution loop logic (will be extended by reasoning/execution engines in Milestones 3-6)
            context.incrementIteration();
            context.log("Completed step 1 execution");
            
            oldState = context.getState();
            if (context.transitionState(AgentState.COMPLETED)) {
                notifyStateTransition(oldState, AgentState.COMPLETED);
            }

            AgentResult result = AgentResult.builder()
                    .agentId(agentId)
                    .sessionId(context.getSessionId())
                    .finalState(AgentState.COMPLETED)
                    .output("Agent execution completed successfully for prompt: " + context.getConfig().getUserPrompt())
                    .totalIterations(context.getCurrentIteration())
                    .executionTimeMs(Duration.between(startTime, Instant.now()).toMillis())
                    .toolsInvoked(context.getToolsInvoked())
                    .metrics(Collections.singletonMap("iterations", context.getCurrentIteration()))
                    .completedAt(Instant.now())
                    .build();

            if (listener != null) {
                listener.onCompleted(context, result);
            }
            return result;

        } catch (Exception e) {
            log.error("Execution failed for agent {}", agentId, e);
            context.setLastError(e.getMessage());
            context.transitionState(AgentState.FAILED);
            if (listener != null) {
                listener.onError(context, e);
            }
            throw new AgentExecutionException("Execution failed: " + e.getMessage(), e, agentId, context.getSessionId());
        }
    }

    @Override
    public synchronized void pause() {
        if (context != null && context.getState() == AgentState.RUNNING) {
            AgentState oldState = context.getState();
            if (context.transitionState(AgentState.PAUSED)) {
                notifyStateTransition(oldState, AgentState.PAUSED);
                context.log("Agent execution paused");
            }
        }
    }

    @Override
    public synchronized void resume() {
        if (context != null && context.getState() == AgentState.PAUSED) {
            AgentState oldState = context.getState();
            if (context.transitionState(AgentState.RUNNING)) {
                notifyStateTransition(oldState, AgentState.RUNNING);
                context.log("Agent execution resumed");
            }
        }
    }

    private void notifyStateTransition(AgentState oldState, AgentState newState) {
        if (listener != null) {
            listener.onStateTransition(context, oldState, newState);
        }
    }
}
