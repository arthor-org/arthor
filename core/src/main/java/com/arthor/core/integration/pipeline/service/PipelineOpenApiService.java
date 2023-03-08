package com.arthor.core.integration.pipeline.service;

import com.arthor.core.integration.pipeline.model.*;

public interface PipelineOpenApiService {

    Boolean pipelineExist(PipelineDetailRequest request);

    String nextBuildId(PipelineNextBuildIdRequest request);

    void createPipeline(PipelineCreateRequest request);

    void buildWithParameters(PipelineBuildRequest request);

    PipelineBuildInfoDTO buildInfo(PipelineBuildInfoRequest request);

}
