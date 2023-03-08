package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.integration.pipeline.model.PipelineNextBuildIdRequest;

public class JenkinsNextBuildIdRequest extends PipelineNextBuildIdRequest {

    public JenkinsNextBuildIdRequest(String jobName) {
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
