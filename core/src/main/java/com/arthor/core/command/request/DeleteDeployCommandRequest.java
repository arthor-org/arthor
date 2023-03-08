package com.arthor.core.command.request;

import com.arthor.core.command.request.AbstractDeployCommandRequest;
import com.arthor.core.common.enumeration.OperateEnum;

public class DeleteDeployCommandRequest extends AbstractDeployCommandRequest {

    public DeleteDeployCommandRequest() {
        super(OperateEnum.DELETE);
    }

    /**
     * 目标id
     */
    private Long targetId;
    /**
     * 关联部署id
     */
    private Long relatedId;

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Long getRelatedId() {
        return relatedId;
    }

    public void setRelatedId(Long relatedId) {
        this.relatedId = relatedId;
    }
}
