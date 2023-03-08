package com.arthor.core.command.request;

import com.arthor.core.deploy.Deployment;

public class DeleteExecuteCommandRequest extends AbstractExecuteCommandRequest {

    private Deployment deployment;

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
