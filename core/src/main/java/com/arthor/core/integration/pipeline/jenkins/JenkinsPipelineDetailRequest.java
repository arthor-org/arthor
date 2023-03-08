package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.integration.pipeline.model.PipelineDetailRequest;

public class JenkinsPipelineDetailRequest extends PipelineDetailRequest {

    public JenkinsPipelineDetailRequest(String jobName) {
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
