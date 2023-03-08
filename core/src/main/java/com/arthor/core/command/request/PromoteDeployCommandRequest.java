package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;

public class PromoteDeployCommandRequest extends AbstractDeployCommandRequest {

    public PromoteDeployCommandRequest() {
        super(OperateEnum.PROMOTE);
    }

    private Long targetId;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
