package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.LifecycleEnum.RUNNING;

public class UncoupledDeployExecuteCommand extends AbstractDeployExecuteCommand {

    @Override
    protected boolean shouldProcessService() {
        return Boolean.FALSE;
    }

    @Override
    protected void processService(CommandContext commandContext) {

    }

    @Override
    protected boolean shouldProcessRoute() {
        return deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && RUNNING == deployment.getLifecycle()
                && PROCESSING == deployment.getRouteStatus();
    }

    @Override
    protected boolean shouldFinishDeploy() {
        return (!deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && RUNNING == deployment.getLifecycle())
                ||
                (deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && RUNNING == deployment.getLifecycle()
                && SUCCESS == deployment.getRouteStatus());
    }

}
