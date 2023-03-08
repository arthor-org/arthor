package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class PipelineCreateRequest extends BasePipelineRequest {

    public PipelineCreateRequest(PipelineTypeEnum pipelineType) {
        super(pipelineType);
    }
}
