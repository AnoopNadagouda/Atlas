package com.atlas.agent.config;

import com.atlas.domain.agent.AgentContext;
import com.atlas.domain.agent.AgentLifecycleListener;
import com.atlas.domain.agent.AgentResult;
import com.atlas.domain.agent.AgentState;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class AgentCoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public AgentLifecycleListener defaultAgentLifecycleListener() {
        return new AgentLifecycleListener() {
            @Override
            public void onStateTransition(AgentContext context, AgentState previousState, AgentState newState) {
                log.info("Agent [{}] transitioned state: {} -> {}", context.getAgentId(), previousState, newState);
            }

            @Override
            public void onStepCompleted(AgentContext context, int stepNumber, String stepDetails) {
                log.info("Agent [{}] completed step {}: {}", context.getAgentId(), stepNumber, stepDetails);
            }

            @Override
            public void onError(AgentContext context, Throwable throwable) {
                log.error("Agent [{}] encountered error: {}", context.getAgentId(), throwable.getMessage());
            }

            @Override
            public void onCompleted(AgentContext context, AgentResult result) {
                log.info("Agent [{}] completed execution in {} ms", context.getAgentId(), result.getExecutionTimeMs());
            }
        };
    }
}
