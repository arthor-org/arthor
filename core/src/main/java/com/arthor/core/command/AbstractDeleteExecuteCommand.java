package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.DeleteExecuteCommandRequest;
import com.arthor.core.deploy.Deployment;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;

import java.util.List;
import java.util.Objects;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;
import static com.arthor.core.common.enumeration.DeployStatusEnum.WITHDRAWN;
import static com.arthor.core.common.enumeration.LifecycleEnum.CLOSING;

public abstract class AbstractDeleteExecuteCommand
        extends AbstractExecuteCommand<DeleteExecuteCommandRequest, Void> {

    protected Deployment deployment;
    protected Deployment relatedDeployment;

    @Override
    public CommandResult<Void> execute(CommandContext commandContext, DeleteExecuteCommandRequest input) {
        // 设置
        setDeployment(input.getDeployment());

        if (Objects.nonNull(deployment.getRelatedDeploymentId())) {
            Deployment relatedDeployment = commandContext.getDeploymentService().findById(deployment.getRelatedDeploymentId());
            setRelatedDeployment(relatedDeployment);
        }

        if (skipProcess()) {
            return CommandResult.success();
        }

        // 处理Route
        if (shouldProcessRoute()) {
            processRoute(commandContext);
        }
        // 处理Service
        if (shouldProcessService()) {
            processService(commandContext);
        }
        // 处理Metadata
        if (shouldProcessMetadata()) {
            processMetadata(commandContext);
        }
        // 处理部署
        if (shouldProcessDeployment()) {
            processDeployment(commandContext);
        }
        // 是否完成部署了
        if (shouldFinishDeploy()) {
            finishDeploy(commandContext);
        }

        return CommandResult.success();
    }

    /**
     * 是否跳过本次处理
     *
     * @return
     */
    protected boolean skipProcess() {
        return Objects.nonNull(relatedDeployment)
                && DEPLOYED != relatedDeployment.getStatus()
                && WITHDRAWN != relatedDeployment.getStatus();
    }

    /**
     * 是否处理Route
     *
     * @return
     */
    protected boolean shouldProcessRoute() {
        return deployment.getExternal()
                && PROCESSING == deployment.getRouteStatus();
    }

    /**
     * 处理Route
     *
     * @param commandContext
     */
    protected void processRoute(CommandContext commandContext) {
        boolean finish = deleteRoute(commandContext, deployment);
        if (finish) {
            deployment.setRouteStatus(SUCCESS);
            commandContext.getDeploymentService().updateDeployment(
                    buildUpdateIngressStatusRequest(deployment.getId(), SUCCESS));
        }
    }

    /**
     * 是否处理Service
     *
     * @return
     */
    protected boolean shouldProcessService() {
        return deployment.getExternal()
                && SUCCESS == deployment.getRouteStatus()
                && PROCESSING == deployment.getServiceStatus();
    }

    protected void processService(CommandContext commandContext) {
        boolean finish = checkDeleteService(commandContext, deployment);
        if (finish) {
            deployment.setServiceStatus(SUCCESS);
            commandContext.getDeploymentService().updateDeployment(
                    buildUpdateServiceStatusRequest(deployment.getId(), SUCCESS));
        }
    }

    /**
     * 是否处理元数据
     *
     * @return
     */
    protected abstract boolean shouldProcessMetadata();

    /**
     * 处理元数据
     *
     * @param commandContext
     */
    protected void processMetadata(CommandContext commandContext) {
        List<V1Pod> v1Pods = commandContext.getKubernetesOpenApiService().listPods(deployment.getNamespace(),
                deployment.getDeploymentName());
        for (V1Pod v1Pod : v1Pods) {
            commandContext.getRegistryOpenApiService().update(buildUpdateMetadataRequest(deployment.getApplicationName(),
                    deployment.getDeploymentName(), deployment.getSymbol(), v1Pod.getStatus().getPodIP(),
                    commandContext.getKubernetesProperties().getDefaultPodPort(),
                    CLOSING, deployment.getCanary(), deployment.getCanaryType(), deployment.getCanaryValue()));
        }
        commandContext.getDeploymentService().updateDeployment(
                buildUpdateLifecycleRequest(deployment.getId(), CLOSING));
    }

    /**
     * 是否处理Deployment
     *
     * @return
     */
    protected abstract boolean shouldProcessDeployment();

    /**
     * 处理Deployment
     *
     * @param commandContext
     */
    protected void processDeployment(CommandContext commandContext) {
        V1Deployment v1Deployment =
                commandContext.getKubernetesOpenApiService().deploymentDetail(deployment.getNamespace(),
                        deployment.getDeploymentName());
        if (Objects.nonNull(v1Deployment)) {
            commandContext.getKubernetesOpenApiService().deleteDeployment(deployment.getNamespace(),
                    deployment.getDeploymentName());
        } else {
            deployment.setDeploymentStatus(SUCCESS);
            commandContext.getDeploymentService().updateDeployment(
                    buildUpdateDeploymentStatusRequest(deployment.getId(), SUCCESS));
        }
    }

    /**
     * 是否完成部署
     *
     * @return
     */
    protected abstract boolean shouldFinishDeploy();

    /**
     * 完成部署
     *
     * @param commandContext
     */
    protected void finishDeploy(CommandContext commandContext) {
        commandContext.getDeploymentService().updateDeployment(
                buildUpdateDeployStatusRequest(deployment.getId(), WITHDRAWN));
    }


    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public void setRelatedDeployment(Deployment relatedDeployment) {
        this.relatedDeployment = relatedDeployment;
    }
}
