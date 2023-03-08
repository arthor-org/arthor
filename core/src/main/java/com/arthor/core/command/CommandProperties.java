package com.arthor.core.command;

import com.arthor.core.common.enumeration.DeployModeEnum;

public class CommandProperties {

    private DeployModeEnum deployMode;

    public DeployModeEnum getDeployMode() {
        return deployMode;
    }

    public void setDeployMode(DeployModeEnum deployMode) {
        this.deployMode = deployMode;
    }
}
