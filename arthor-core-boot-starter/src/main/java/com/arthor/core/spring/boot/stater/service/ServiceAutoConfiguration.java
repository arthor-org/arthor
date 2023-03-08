package com.arthor.core.spring.boot.stater.service;

import com.arthor.core.application.service.ApplicationService;
import com.arthor.core.application.service.DefaultApplicationService;
import com.arthor.core.application.store.ApplicationStore;
import com.arthor.core.build.service.BuildRecordService;
import com.arthor.core.build.service.DefaultBuildRecordService;
import com.arthor.core.build.store.BuildRecordStore;
import com.arthor.core.counter.service.CounterService;
import com.arthor.core.counter.service.DefaultCounterService;
import com.arthor.core.counter.store.CounterStore;
import com.arthor.core.deploy.service.DefaultDeployRecordService;
import com.arthor.core.deploy.service.DefaultDeploymentService;
import com.arthor.core.deploy.service.DeployRecordService;
import com.arthor.core.deploy.service.DeploymentService;
import com.arthor.core.deploy.store.DeployRecordStore;
import com.arthor.core.deploy.store.DeploymentStore;
import com.arthor.core.env.service.DefaultEnvService;
import com.arthor.core.env.service.EnvService;
import com.arthor.core.env.store.EnvStore;
import com.arthor.core.feature.service.DefaultFeatureService;
import com.arthor.core.feature.service.FeatureService;
import com.arthor.core.feature.store.FeatureStore;
import com.arthor.core.lock.service.DefaultLockService;
import com.arthor.core.lock.service.LockService;
import com.arthor.core.lock.store.LockStore;
import com.arthor.core.pipeline.service.DefaultPipelineService;
import com.arthor.core.pipeline.service.PipelineService;
import com.arthor.core.pipeline.store.PipelineStore;
import com.arthor.core.spring.boot.stater.store.StoreAutoConfiguration;
import com.arthor.core.user.service.DefaultUserService;
import com.arthor.core.user.service.UserService;
import com.arthor.core.user.store.UserStore;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

@AutoConfigureBefore(StoreAutoConfiguration.class)
public class ServiceAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(ApplicationService.class)
    public ApplicationService applicationService(ApplicationStore applicationStore) {
        return new DefaultApplicationService(applicationStore);
    }

    @Bean
    @ConditionalOnMissingBean(FeatureService.class)
    public FeatureService featureService(FeatureStore featureStore) {
        return new DefaultFeatureService(featureStore);
    }

    @Bean
    @ConditionalOnMissingBean(BuildRecordService.class)
    public BuildRecordService buildRecordService(BuildRecordStore buildRecordStore) {
        return new DefaultBuildRecordService(buildRecordStore);
    }


    @Bean
    @ConditionalOnMissingBean(DeploymentService.class)
    public DeploymentService deploymentService(DeploymentStore deploymentStore) {
        return new DefaultDeploymentService(deploymentStore);
    }

    @Bean
    @ConditionalOnMissingBean(DeployRecordService.class)
    public DeployRecordService deployRecordService(DeployRecordStore deployRecordStore) {
        return new DefaultDeployRecordService(deployRecordStore);
    }

    @Bean
    @ConditionalOnMissingBean(CounterService.class)
    public CounterService counterService(CounterStore counterStore) {
        return new DefaultCounterService(counterStore);
    }

    @Bean
    @ConditionalOnMissingBean(LockService.class)
    public LockService lockService(LockStore lockStore) {
        return new DefaultLockService(lockStore);
    }

    @Bean
    @ConditionalOnMissingBean(PipelineService.class)
    public PipelineService pipelineService(PipelineStore pipelineStore) {
        return new DefaultPipelineService(pipelineStore);
    }

    @Bean
    @ConditionalOnMissingBean(EnvService.class)
    public EnvService envService(EnvStore envStore) {
        return new DefaultEnvService(envStore);
    }

    @Bean
    @ConditionalOnMissingBean(UserService.class)
    public UserService userService(UserStore userStore) {
        return new DefaultUserService(userStore);
    }

}
