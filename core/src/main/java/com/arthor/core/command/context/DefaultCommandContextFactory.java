package com.arthor.core.command.context;

import com.arthor.core.build.service.BuildRecordService;
import com.arthor.core.command.CommandProperties;
import com.arthor.core.command.executor.CommandExecutor;
import com.arthor.core.command.factory.CommandFactory;
import com.arthor.core.counter.service.CounterService;
import com.arthor.core.deploy.service.DeployRecordService;
import com.arthor.core.deploy.service.DeploymentService;
import com.arthor.core.env.service.EnvService;
import com.arthor.core.integration.kubernetes.KubernetesProperties;
import com.arthor.core.integration.kubernetes.service.KubernetesOpenApiService;
import com.arthor.core.integration.route.service.RouteOpenApiService;
import com.arthor.core.registry.service.RegistryOpenApiService;

public class DefaultCommandContextFactory implements CommandContextFactory {

    private final DeploymentService deploymentService;
    private final DeployRecordService deployRecordService;
    private final BuildRecordService buildRecordService;
    private final EnvService envService;
    private final CounterService counterService;
    private final KubernetesOpenApiService kubernetesOpenApiService;
    private final KubernetesProperties kubernetesProperties;
    private final RegistryOpenApiService registryOpenApiService;
    private final RouteOpenApiService routeOpenApiService;
    private final CommandFactory commandFactory;
    private final CommandProperties commandProperties;

    public DefaultCommandContextFactory(DeploymentService deploymentService,
                                        DeployRecordService deployRecordService,
                                        BuildRecordService buildRecordService,
                                        EnvService envService,
                                        CounterService counterService,
                                        KubernetesOpenApiService kubernetesOpenApiService,
                                        RegistryOpenApiService registryOpenApiService,
                                        RouteOpenApiService routeOpenApiService,
                                        CommandFactory commandFactory,
                                        CommandProperties commandProperties) {
        this.deploymentService = deploymentService;
        this.deployRecordService = deployRecordService;
        this.buildRecordService = buildRecordService;
        this.envService = envService;
        this.counterService = counterService;
        this.kubernetesOpenApiService = kubernetesOpenApiService;
        this.kubernetesProperties = kubernetesOpenApiService.getProperties();
        this.registryOpenApiService = registryOpenApiService;
        this.routeOpenApiService = routeOpenApiService;
        this.commandFactory = commandFactory;
        this.commandProperties = commandProperties;
    }

    @Override
    public CommandContext createCommandContext(CommandExecutor commandExecutor) {
        return new CommandContext(deploymentService, deployRecordService,
                buildRecordService, envService, counterService, kubernetesOpenApiService, kubernetesProperties,
                registryOpenApiService, routeOpenApiService, commandFactory, commandExecutor, commandProperties);
    }
}
