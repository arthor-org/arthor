package com.arthor.core.command;

import com.alibaba.fastjson.JSON;
import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.DeployExecuteCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.deploy.DefaultPod;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.Pod;
import com.google.common.collect.Lists;
import io.kubernetes.client.openapi.models.V1Deployment;
import io.kubernetes.client.openapi.models.V1Pod;

import java.util.List;
import java.util.Objects;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;
import static com.arthor.core.common.enumeration.LifecycleEnum.*;

public abstract class AbstractDeployExecuteCommand
        extends AbstractExecuteCommand<DeployExecuteCommandRequest, Void> {

    protected Deployment deployment;

    @Override
    public CommandResult<Void> execute(CommandContext commandContext, DeployExecuteCommandRequest input) {
        // 设置参数
        Deployment deployment = input.getDeployment();
        setDeployment(deployment);

        // 首先处理Deployment
        if (shouldProcessDeployment()) {
            processDeployment(commandContext);
        }
        // 处理Metadata
        if (shouldProcessMetadata()) {
            processMetadata(commandContext);
        }
        // 处理Service
        if (shouldProcessService()) {
            processService(commandContext);
        }
        // 处理Route
        if (shouldProcessRoute()) {
            processRoute(commandContext);
        }
        // 是否完成部署了
        if (shouldFinishDeploy()) {
            finishDeploy(commandContext);
        }
        return CommandResult.success();
    }

    /**
     * 判断是否需要处理部署
     *
     * @return
     */
    protected boolean shouldProcessDeployment() {
        return PROCESSING == deployment.getDeploymentStatus();
    }

    /**
     * 处理部署
     *
     * @param commandContext
     */
    protected void processDeployment(CommandContext commandContext) {
        V1Deployment v1Deployment =
                commandContext.getKubernetesOpenApiService().deploymentDetail(deployment.getNamespace(),
                        deployment.getDeploymentName());
        if (Objects.isNull(v1Deployment)) {
            commandContext.getKubernetesOpenApiService().createDeployment(deployment.getNamespace(),
                    deployment.getDeploymentName(),
                    deployment.getDeploymentImageId(), deployment.getDeploymentReplicas());
        } else {
            if (checkDeployment(v1Deployment, deployment.getDeploymentReplicas(), deployment.getDeploymentImageId())) {
                deployment.setDeploymentStatus(SUCCESS); // 保持更好的传递性
                commandContext.getDeploymentService().updateDeployment(
                        buildUpdateDeploymentInfoRequest(deployment.getId(),
                                v1Deployment.getStatus().getReadyReplicas(),
                                v1Deployment.getStatus().getUpdatedReplicas(),
                                v1Deployment.getStatus().getAvailableReplicas(),
                                v1Deployment.getStatus().getUnavailableReplicas(), SUCCESS)
                );
            }
        }
    }

    /**
     * 判断是否处理元数据
     *
     * @return
     */
    protected boolean shouldProcessMetadata() {
        return SUCCESS == deployment.getDeploymentStatus()
                && (CREATING == deployment.getLifecycle()
                    || MODIFYING == deployment.getLifecycle());
    }

    /**
     * 处理元数据
     *
     * @param commandContext
     */
    protected void processMetadata(CommandContext commandContext) {
        List<V1Pod> v1Pods = commandContext.getKubernetesOpenApiService().listPods(deployment.getNamespace(),
                deployment.getDeploymentName());
        List<Pod> pods = Lists.newArrayList();
        for (V1Pod v1Pod : v1Pods) {
            commandContext.getRegistryOpenApiService().update(buildUpdateMetadataRequest(deployment.getApplicationName(),
                    deployment.getDeploymentName(), deployment.getSymbol(), v1Pod.getStatus().getPodIP(),
                    commandContext.getKubernetesProperties().getDefaultPodPort(),
                    RUNNING, deployment.getCanary(), deployment.getCanaryType(), deployment.getCanaryValue()));
            DefaultPod pod = new DefaultPod();
            pod.setIp(v1Pod.getStatus().getPodIP());
            pod.setPort(commandContext.getKubernetesProperties().getDefaultPodPort());
            pod.setName(Objects.nonNull(v1Pod.getMetadata()) ? v1Pod.getMetadata().getName() : ALL.UNKNOWN);
            pods.add(pod);
        }
        String podsJson = JSON.toJSONString(pods);
        deployment.setLifecycle(RUNNING); // 保持更好的传递性
        deployment.setPods(podsJson);
        commandContext.getDeploymentService().updateDeployment(
                buildUpdateLifecycleRequest(deployment.getId(), podsJson, RUNNING));
    }

    /**
     * 是否处理Service
     *
     * @return
     */
    protected boolean shouldProcessService() {
        return deployment.getExternal()
                && PROCESSING == deployment.getServiceStatus();
    }

    /**
     * 处理Service
     *
     * @param commandContext
     */
    protected void processService(CommandContext commandContext) {
        boolean finish = checkDeployService(commandContext, deployment);
        if (finish) {
            deployment.setServiceStatus(SUCCESS); // 保持更好的传递性
            commandContext.getDeploymentService().updateDeployment(
                    buildUpdateServiceStatusRequest(deployment.getId(), SUCCESS));
        }
    }

    /**
     * 是否处理Route
     *
     * @return
     */
    protected abstract boolean shouldProcessRoute();

    /**
     * 处理Route
     *
     * @param commandContext
     */
    protected void processRoute(CommandContext commandContext) {
        boolean finish = deployRoute(commandContext, deployment);
        if (finish) {
            deployment.setRouteStatus(SUCCESS);
            commandContext.getDeploymentService().updateDeployment(
                    buildUpdateIngressStatusRequest(deployment.getId(), SUCCESS));
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
                buildUpdateDeployStatusRequest(deployment.getId(), DEPLOYED));
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
