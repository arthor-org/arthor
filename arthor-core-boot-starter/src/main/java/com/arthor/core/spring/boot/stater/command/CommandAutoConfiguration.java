package com.arthor.core.spring.boot.stater.command;

import com.arthor.core.build.service.BuildRecordService;
import com.arthor.core.command.CommandProperties;
import com.arthor.core.command.context.CommandContextFactory;
import com.arthor.core.command.context.DefaultCommandContextFactory;
import com.arthor.core.command.executor.CommandExecutor;
import com.arthor.core.command.executor.DefaultCommandExecutor;
import com.arthor.core.command.factory.CommandFactory;
import com.arthor.core.command.factory.NativeCommandFactory;
import com.arthor.core.command.factory.UncoupledCommandFactory;
import com.arthor.core.common.utils.Assert;
import com.arthor.core.counter.service.CounterService;
import com.arthor.core.deploy.service.DeployRecordService;
import com.arthor.core.deploy.service.DeploymentService;
import com.arthor.core.env.service.EnvService;
import com.arthor.core.integration.kubernetes.service.KubernetesOpenApiService;
import com.arthor.core.integration.route.service.RouteOpenApiService;
import com.arthor.core.registry.service.RegistryOpenApiService;
import com.arthor.core.spring.boot.stater.properties.CommandAutoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties({ CommandAutoProperties.class })
public class CommandAutoConfiguration implements InitializingBean {

    private final CommandAutoProperties commandAutoProperties;

    @Bean
    @ConditionalOnMissingBean(CommandFactory.class)
    @ConditionalOnProperty(value = "command.deployMode", havingValue = "NATIVE", matchIfMissing = true)
    public CommandFactory nativeCommandFactory() {
        return new NativeCommandFactory();
    }

    @Bean
    @ConditionalOnMissingBean(CommandFactory.class)
    @ConditionalOnProperty(value = "command.deployMode", havingValue = "UNCOUPLED")
    public UncoupledCommandFactory uncoupledCommandFactory() {
        return new UncoupledCommandFactory();
    }

    @Bean
    @ConditionalOnMissingBean(CommandExecutor.class)
    public CommandExecutor commandExecutor(CommandContextFactory commandContextFactory) {
        return new DefaultCommandExecutor(commandContextFactory);
    }

    @Bean
    @ConditionalOnMissingBean(CommandContextFactory.class)
    public CommandContextFactory commandContextFactory(DeploymentService deploymentService,
                                                       DeployRecordService deployRecordService,
                                                       BuildRecordService buildRecordService,
                                                       EnvService envService,
                                                       CounterService counterService,
                                                       KubernetesOpenApiService kubernetesOpenApiService,
                                                       RegistryOpenApiService registryOpenApiService,
                                                       RouteOpenApiService routeOpenApiService,
                                                       CommandFactory commandFactory) {
        return new DefaultCommandContextFactory(deploymentService, deployRecordService,
                buildRecordService, envService, counterService, kubernetesOpenApiService,
                registryOpenApiService, routeOpenApiService, commandFactory, convert());
    }

    private CommandProperties convert() {
        CommandProperties commandProperties = new CommandProperties();
        commandProperties.setDeployMode(commandAutoProperties.getDeployMode());
        return commandProperties;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(commandAutoProperties.getDeployMode()
                , "请配置command.deployMode");
    }
}
