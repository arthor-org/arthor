package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.OperateEnum;

public abstract class AbstractDeployCommandRequest {

    private final OperateEnum operate;

    public AbstractDeployCommandRequest(OperateEnum operate) {
        this.operate = operate;
    }

    public OperateEnum getOperate() {
        return operate;
    }

}
