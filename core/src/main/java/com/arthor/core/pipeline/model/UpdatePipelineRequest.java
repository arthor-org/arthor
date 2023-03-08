package com.arthor.core.pipeline.model;

import com.arthor.core.common.enumeration.PipelineStatusEnum;

public class UpdatePipelineRequest {

    private Long id;

    private PipelineStatusEnum status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PipelineStatusEnum getStatus() {
        return status;
    }

    public void setStatus(PipelineStatusEnum status) {
        this.status = status;
    }
}
