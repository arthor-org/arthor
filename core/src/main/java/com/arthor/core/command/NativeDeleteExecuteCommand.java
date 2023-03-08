
package com.arthor.core.command;

import com.arthor.core.common.utils.TimeUtils;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.LifecycleEnum.CLOSING;
import static com.arthor.core.common.enumeration.LifecycleEnum.RUNNING;

public class NativeDeleteExecuteCommand extends AbstractDeleteExecuteCommand {

    @Override
    protected boolean shouldProcessMetadata() {
        return (!deployment.getExternal()
                && RUNNING == deployment.getLifecycle())
                ||
                (deployment.getExternal()
                && SUCCESS == deployment.getRouteStatus()
                && SUCCESS == deployment.getServiceStatus()
                && RUNNING == deployment.getLifecycle());
    }

    @Override
    protected boolean shouldProcessDeployment() {
        return ((!deployment.getExternal()
                && CLOSING == deployment.getLifecycle()
                && PROCESSING == deployment.getDeploymentStatus())
                ||
                (deployment.getExternal()
                        && SUCCESS == deployment.getRouteStatus()
                        && SUCCESS == deployment.getServiceStatus()
                        && CLOSING == deployment.getLifecycle()
                        && PROCESSING == deployment.getDeploymentStatus()))
                && TimeUtils.isAfter(deployment.getShutdownTime(), 60);
    }

    @Override
    protected boolean shouldFinishDeploy() {
        return (!deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && CLOSING == deployment.getLifecycle())
                ||
                (deployment.getExternal()
                        && SUCCESS == deployment.getRouteStatus()
                        && SUCCESS == deployment.getServiceStatus()
                        && CLOSING == deployment.getLifecycle()
                        && SUCCESS == deployment.getDeploymentStatus());
    }

}

