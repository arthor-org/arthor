package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

public abstract class CommandRequest {

    public static CreateDeployCommandRequest buildCreateDeployCommandRequest(Long buildRecordId, Boolean external,
                                                                             String symbol, Integer replicas,
                                                                             String imagePullPolicy, Integer podPort,
                                                                             OperateEnum operate) {
        CreateDeployCommandRequest createDeployCommandRequest = new CreateDeployCommandRequest(operate);
        createDeployCommandRequest.setBuildRecordId(buildRecordId);
        createDeployCommandRequest.setExternal(external);
        createDeployCommandRequest.setSymbol(symbol);
        createDeployCommandRequest.setReplicas(replicas);
        createDeployCommandRequest.setImagePullPolicy(imagePullPolicy);
        createDeployCommandRequest.setPodPort(podPort);
        createDeployCommandRequest.setCanary(Boolean.FALSE);
        return createDeployCommandRequest;
    }

    public static CreateDeployCommandRequest buildCreateDeployCommandRequest(Long buildRecordId, Boolean external,
                                                                             String symbol, Integer replicas,
                                                                             String imagePullPolicy, Integer podPort,
                                                                             CanaryTypeEnum canaryType, String canaryValue,
                                                                             OperateEnum operate) {
        CreateDeployCommandRequest createDeployCommandRequest = new CreateDeployCommandRequest(operate);
        createDeployCommandRequest.setBuildRecordId(buildRecordId);
        createDeployCommandRequest.setExternal(external);
        createDeployCommandRequest.setSymbol(symbol);
        createDeployCommandRequest.setReplicas(replicas);
        createDeployCommandRequest.setImagePullPolicy(imagePullPolicy);
        createDeployCommandRequest.setPodPort(podPort);
        createDeployCommandRequest.setCanary(Boolean.TRUE);
        createDeployCommandRequest.setCanaryType(canaryType);
        createDeployCommandRequest.setCanaryValue(canaryValue);
        return createDeployCommandRequest;
    }

    public static ReplaceDeployCommandRequest buildReplaceDeployCommandRequest(Deployment deployment) {
        ReplaceDeployCommandRequest replaceDeployCommandRequest = new ReplaceDeployCommandRequest();
        replaceDeployCommandRequest.setDeployment(deployment);
        return replaceDeployCommandRequest;
    }

}
