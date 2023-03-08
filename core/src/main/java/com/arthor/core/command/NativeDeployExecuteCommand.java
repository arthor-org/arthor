package com.arthor.core.command;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.LifecycleEnum.RUNNING;

public class NativeDeployExecuteCommand extends AbstractDeployExecuteCommand {

    @Override
    protected boolean shouldProcessRoute() {
        return deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && RUNNING == deployment.getLifecycle()
                && SUCCESS == deployment.getServiceStatus()
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
                && SUCCESS == deployment.getServiceStatus()
                && SUCCESS == deployment.getRouteStatus());
    }

}
