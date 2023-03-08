package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class PipelineNextBuildIdRequest extends BasePipelineRequest {

    public PipelineNextBuildIdRequest(PipelineTypeEnum pipelineType) {
        super(pipelineType);
    }
}
