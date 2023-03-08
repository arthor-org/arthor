package com.arthor.core.pipeline.model;

import com.arthor.core.common.enumeration.PipelineStatusEnum;

public class ListPipelineRequest {

    private PipelineStatusEnum status;

    public PipelineStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PipelineStatusEnum status) {
        this.status = status;
    }
}
