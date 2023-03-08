package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;

public class UpdateBlueGreenDeployCommandRequest extends AbstractDeployCommandRequest {

    public UpdateBlueGreenDeployCommandRequest() {
        super(OperateEnum.UPDATE_BLUE_GREEN);
    }

    /**
     * 绿部署id
     */
    private Long greenDeploymentId;
    /**
     * 蓝绿权重
     */
    private String weight;

    public Long getGreenDeploymentId() {
        return greenDeploymentId;
    }

    public void setGreenDeploymentId(Long greenDeploymentId) {
        this.greenDeploymentId = greenDeploymentId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
