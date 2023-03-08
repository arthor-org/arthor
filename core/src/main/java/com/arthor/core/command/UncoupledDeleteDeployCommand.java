package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.DeleteDeployCommandRequest;
import com.arthor.core.deploy.model.CreateDeploymentRequest;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DELETING;

public class UncoupledDeleteDeployCommand extends AbstractDeleteDeployCommand {

    @Override
    protected CreateDeploymentRequest buildDeleteDeploymentRequest(CommandContext commandContext,
                                                                   DeleteDeployCommandRequest input) {
        CreateDeploymentRequest deleteDeploymentRequest = new CreateDeploymentRequest();
        deleteDeploymentRequest.setId(input.getTargetId());
        deleteDeploymentRequest.setTick(tick);
        deleteDeploymentRequest.setStatus(DELETING);
        deleteDeploymentRequest.setDeploymentStatus(PROCESSING);
        deleteDeploymentRequest.setRelatedDeploymentId(input.getRelatedId());
        deleteDeploymentRequest.setRouteStatus(deployment.getExternal() ? PROCESSING : null);
        return deleteDeploymentRequest;
    }
}
