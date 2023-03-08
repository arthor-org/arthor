package com.arthor.server.scheduler;

import com.arthor.server.facade.ApplicationFacade;
import com.arthor.server.facade.BuildFacade;
import com.arthor.server.facade.DeploymentFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

/**
 * 基于Spring的定时调度,先简单实现一下
 */
@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
public class SchedulerConfiguration {

    @Bean
    public BuildCheckScheduler buildCheckScheduler(BuildFacade buildFacade) {
        return new BuildCheckScheduler(buildFacade);
    }

    @Bean
    public PipelineCheckScheduler pipelineCheckScheduler(ApplicationFacade applicationFacade) {
        return new PipelineCheckScheduler(applicationFacade);
    }

    @Bean
    public DeployCheckScheduler deployCheckScheduler(DeploymentFacade deploymentFacade) {
        return new DeployCheckScheduler(deploymentFacade);
    }

}
