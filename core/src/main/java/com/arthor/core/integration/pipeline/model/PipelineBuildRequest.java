package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class PipelineBuildRequest extends BasePipelineRequest {

    public PipelineBuildRequest(PipelineTypeEnum pipelineType) {
        super(pipelineType);
    }
}
