package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;

public class RollbackDeployCommandRequest extends AbstractDeployCommandRequest {

    public RollbackDeployCommandRequest() {
        super(OperateEnum.ROLLBACK);
    }

    private Long targetId;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }
}
