package com.arthor.core.command;

import com.arthor.core.deploy.model.ListDeploymentRequest;

import java.util.List;

import static com.arthor.core.common.enumeration.DeployStatusEnum.*;

public class DefaultDispatchExecuteCommand extends AbstractDispatchExecuteCommand {

    @Override
    protected ListDeploymentRequest buildListDeploymentRequest() {
        ListDeploymentRequest listDeploymentRequest = new ListDeploymentRequest();
        listDeploymentRequest.setStatusList(List.of(DEPLOYING, DELETING, REPLACING));
        return listDeploymentRequest;
    }

}
