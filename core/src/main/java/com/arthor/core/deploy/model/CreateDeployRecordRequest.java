package com.arthor.core.deploy.model;

import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

public class CreateDeployRecordRequest {

    private OperateEnum operate;

    private Deployment target;

    private Deployment related;

    public OperateEnum getOperate() {
        return operate;
    }

    public void setOperate(OperateEnum operate) {
        this.operate = operate;
    }

    public Deployment getTarget() {
        return target;
    }

    public void setTarget(Deployment target) {
        this.target = target;
    }

    public Deployment getRelated() {
        return related;
    }

    public void setRelated(Deployment related) {
        this.related = related;
    }
}
