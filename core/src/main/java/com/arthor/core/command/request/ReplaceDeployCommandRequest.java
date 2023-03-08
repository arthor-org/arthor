package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

public class ReplaceDeployCommandRequest extends AbstractDeployCommandRequest {

    private Deployment deployment;

    public ReplaceDeployCommandRequest() {
        super(OperateEnum.REPLACE);
    }

    public Deployment getDeployment() {
        return deployment;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }
}
