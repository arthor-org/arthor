package com.arthor.core.pipeline;

import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.common.enumeration.PipelineTypeEnum;

public interface Pipeline {

    Long getId();

    void setId(Long id);

    String getJobName();

    void setJobName(String jobName);

    PipelineTypeEnum getType();

    void setType(PipelineTypeEnum type);

    PipelineStatusEnum getStatus();

    void setStatus(PipelineStatusEnum status);
    
}
