package com.arthor.core.command;

import com.alibaba.fastjson.JSON;
import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.CreateDeployCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.DeployItemStatusEnum;
import com.arthor.core.common.enumeration.DeployStatusEnum;
import com.arthor.core.common.enumeration.LifecycleEnum;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.store.ext.UncoupledRouteExt;

import java.time.LocalDateTime;

public class UncoupledCreateDeployCommand extends AbstractCreateDeployCommand {

    @Override
    protected CreateDeploymentRequest buildCreateDeploymentRequest(CommandContext commandContext,
                                                                   CreateDeployCommandRequest input) {
        CreateDeploymentRequest createDeploymentRequest = new CreateDeploymentRequest();
        createDeploymentRequest.setApplicationId(buildRecord.getApplicationId());
        createDeploymentRequest.setApplicationName(buildRecord.getApplicationName());
        createDeploymentRequest.setFeatureId(buildRecord.getFeatureId());
        createDeploymentRequest.setBuildRecordId(input.getBuildRecordId());
        createDeploymentRequest.setTick(tick);
        createDeploymentRequest.setEnv(env.getName());
        createDeploymentRequest.setNamespace(env.getName());
        createDeploymentRequest.setHost(env.getHost());
        createDeploymentRequest.setDeploymentName(deploymentName);
        createDeploymentRequest.setDeploymentImageId(buildRecord.getImageId());
        createDeploymentRequest.setDeploymentImagePullPolicy(input.getImagePullPolicy());
        createDeploymentRequest.setDeploymentContainerName(deploymentName);
        createDeploymentRequest.setDeploymentPodLabelName(deploymentName);
        createDeploymentRequest.setDeploymentReplicas(input.getReplicas());
        createDeploymentRequest.setDeploymentContainerPort(input.getPodPort());
        createDeploymentRequest.setStatus(DeployStatusEnum.DEPLOYING);
        createDeploymentRequest.setDeploymentStatus(DeployItemStatusEnum.PROCESSING);
        createDeploymentRequest.setLifecycle(LifecycleEnum.CREATING);
        createDeploymentRequest.setCreateTime(LocalDateTime.now());
        createDeploymentRequest.setSymbol(deploySymbol);
        createDeploymentRequest.setExternal(input.getExternal());
        createDeploymentRequest.setDeployMode(commandContext.getCommandProperties().getDeployMode());
        if (input.getCanary()) {
            createDeploymentRequest.setCanary(Boolean.TRUE);
            createDeploymentRequest.setCanaryType(input.getCanaryType());
            createDeploymentRequest.setCanaryValue(input.getCanaryValue());
        } else {
            createDeploymentRequest.setCanary(Boolean.FALSE);
        }
        if (input.getExternal()) {
            // path由symbol组成,无需区分canary
            String path = ALL.buildSymbol(buildRecord.getApplicationName(), symbol);
            String routeRegexUri = "/" + path + "(/|$)(.*)";
            String routeUri = "/" + path + "*";
            // route名称,对于API解耦来说,路由是同一个,这里无需区分canary了,在plugin中处理
            String routeName = env.getHost() + "-" + ALL.buildSymbol(buildRecord.getApplicationName(), symbol);
            createDeploymentRequest.setRouteName(routeName);
            UncoupledRouteExt uncoupledRouteExt = new UncoupledRouteExt();
            uncoupledRouteExt.setRegularExpression(routeRegexUri);
            createDeploymentRequest.setRouteExt(JSON.toJSONString(uncoupledRouteExt));
            createDeploymentRequest.setRoutePath(routeUri);
            createDeploymentRequest.setRouteStatus(DeployItemStatusEnum.PROCESSING);
        }
        return createDeploymentRequest;
    }
}
