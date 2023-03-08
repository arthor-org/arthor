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

public class CommandContext {

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
    private final CommandExecutor commandExecutor;
    private final CommandProperties commandProperties;

    public CommandContext(DeploymentService deploymentService,
                          DeployRecordService deployRecordService,
                          BuildRecordService buildRecordService,
                          EnvService envService,
                          CounterService counterService,
                          KubernetesOpenApiService kubernetesOpenApiService,
                          KubernetesProperties kubernetesProperties,
                          RegistryOpenApiService registryOpenApiService,
                          RouteOpenApiService routeOpenApiService,
                          CommandFactory commandFactory,
                          CommandExecutor commandExecutor,
                          CommandProperties commandProperties) {
        this.deploymentService = deploymentService;
        this.deployRecordService = deployRecordService;
        this.buildRecordService = buildRecordService;
        this.envService = envService;
        this.counterService = counterService;
        this.kubernetesOpenApiService = kubernetesOpenApiService;
        this.kubernetesProperties = kubernetesProperties;
        this.registryOpenApiService = registryOpenApiService;
        this.routeOpenApiService = routeOpenApiService;
        this.commandFactory = commandFactory;
        this.commandExecutor = commandExecutor;
        this.commandProperties = commandProperties;
    }

    public DeploymentService getDeploymentService() {
        return deploymentService;
    }

    public DeployRecordService getDeployRecordService() {
        return deployRecordService;
    }

    public BuildRecordService getBuildRecordService() {
        return buildRecordService;
    }

    public EnvService getEnvService() {
        return envService;
    }

    public CounterService getCounterService() {
        return counterService;
    }

    public KubernetesOpenApiService getKubernetesOpenApiService() {
        return kubernetesOpenApiService;
    }

    public KubernetesProperties getKubernetesProperties() {
        return kubernetesProperties;
    }

    public RegistryOpenApiService getRegistryOpenApiService() {
        return registryOpenApiService;
    }

    public RouteOpenApiService getRouteOpenApiService() {
        return routeOpenApiService;
    }

    public CommandFactory getCommandFactory() {
        return commandFactory;
    }

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public CommandProperties getCommandProperties() {
        return commandProperties;
    }

}
