package com.arthor.core.integration.pipeline.model;

import com.arthor.core.common.enumeration.PipelineTypeEnum;

public class PipelineBuildInfoRequest extends BasePipelineRequest {

    public PipelineBuildInfoRequest(PipelineTypeEnum pipelineType) {
        super(pipelineType);
    }
}
