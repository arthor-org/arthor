package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;

public class ScaleDeployCommandRequest extends AbstractDeployCommandRequest {

    public ScaleDeployCommandRequest() {
        super(OperateEnum.SCALE);
    }


    /**
     * 目标部署id
     */
    private Long targetId;

    /**
     * 副本数
     */
    private Integer replicas;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }
}
