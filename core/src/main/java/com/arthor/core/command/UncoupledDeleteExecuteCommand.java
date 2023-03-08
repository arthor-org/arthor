
package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.common.utils.TimeUtils;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployItemStatusEnum.SUCCESS;
import static com.arthor.core.common.enumeration.LifecycleEnum.CLOSING;
import static com.arthor.core.common.enumeration.LifecycleEnum.RUNNING;

public class UncoupledDeleteExecuteCommand extends AbstractDeleteExecuteCommand {

    @Override
    protected boolean shouldFinishDeploy() {
        return (!deployment.getExternal()
                && SUCCESS == deployment.getDeploymentStatus()
                && CLOSING == deployment.getLifecycle())
                ||
                (deployment.getExternal()
                        && SUCCESS == deployment.getRouteStatus()
                        && CLOSING == deployment.getLifecycle()
                        && SUCCESS == deployment.getDeploymentStatus());
    }

    @Override
    protected boolean shouldProcessService() {
        return Boolean.FALSE;
    }

    @Override
    protected void processService(CommandContext commandContext) {

    }

    @Override
    protected boolean shouldProcessMetadata() {
        return (!deployment.getExternal()
                && RUNNING == deployment.getLifecycle())
                ||
                (deployment.getExternal()
                && SUCCESS == deployment.getRouteStatus()
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
                        && CLOSING == deployment.getLifecycle()
                        && PROCESSING == deployment.getDeploymentStatus()))
                && TimeUtils.isAfter(deployment.getShutdownTime(), 60);
    }


}

