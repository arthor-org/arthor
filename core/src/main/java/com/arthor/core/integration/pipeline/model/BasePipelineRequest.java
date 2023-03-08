package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class BasePipelineRequest {
    private PipelineTypeEnum pipelineType;

    public BasePipelineRequest(PipelineTypeEnum pipelineType) {
        this.pipelineType = pipelineType;
    }

    public PipelineTypeEnum getPipelineType() {
        return pipelineType;
    }

    public void setPipelineType(PipelineTypeEnum pipelineType) {
        this.pipelineType = pipelineType;
    }
}
