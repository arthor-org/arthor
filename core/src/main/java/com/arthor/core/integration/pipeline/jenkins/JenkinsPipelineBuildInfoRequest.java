package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.integration.pipeline.model.PipelineBuildInfoRequest;

public class JenkinsPipelineBuildInfoRequest extends PipelineBuildInfoRequest {

    private String jobName;
    private String buildNumber;

    public JenkinsPipelineBuildInfoRequest(String jobName, String buildNumber) {
        super(PipelineTypeEnum.JENKINS);
        this.jobName = jobName;
        this.buildNumber = buildNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }
}
