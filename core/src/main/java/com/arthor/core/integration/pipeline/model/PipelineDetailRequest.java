package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class PipelineDetailRequest extends BasePipelineRequest {

    public PipelineDetailRequest(PipelineTypeEnum pipelineType) {
        super(pipelineType);
    }
}
