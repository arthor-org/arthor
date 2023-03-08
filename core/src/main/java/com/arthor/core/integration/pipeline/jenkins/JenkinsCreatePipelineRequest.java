package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.integration.pipeline.model.PipelineCreateRequest;

public class JenkinsCreatePipelineRequest extends PipelineCreateRequest {

    public JenkinsCreatePipelineRequest(String jobName) {
        super(PipelineTypeEnum.JENKINS);
        this.jobName = jobName;
    }

    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

}
