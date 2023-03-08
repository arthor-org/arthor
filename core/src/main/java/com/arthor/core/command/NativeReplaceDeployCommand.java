package com.arthor.core.command;

import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployStatusEnum.REPLACING;

public class NativeReplaceDeployCommand extends AbstractReplaceDeployCommand {

    @Override
    protected CreateDeploymentRequest buildUpdateDeployRequest(Deployment replacedDeployment) {
        CreateDeploymentRequest updateDeploymentRequest = new CreateDeploymentRequest();
        updateDeploymentRequest.setId(replacedDeployment.getId());
        updateDeploymentRequest.setRelatedDeploymentId(deployment.getId());
        updateDeploymentRequest.setStatus(REPLACING);
        updateDeploymentRequest.setDeploymentStatus(PROCESSING);
        updateDeploymentRequest.setServiceStatus(replacedDeployment.getExternal() ? PROCESSING : null);
        updateDeploymentRequest.setRouteStatus(replacedDeployment.getExternal() ? PROCESSING : null);
        return updateDeploymentRequest;
    }

}
