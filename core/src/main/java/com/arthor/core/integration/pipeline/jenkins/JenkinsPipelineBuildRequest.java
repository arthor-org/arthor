package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.integration.pipeline.model.PipelineBuildRequest;

public class JenkinsPipelineBuildRequest extends PipelineBuildRequest {

    private String jobName;
    private String repositoryUrl;
    private String branch;
    private String environment;

    public JenkinsPipelineBuildRequest(String jobName, String repositoryUrl, String branch, String environment) {
        super(PipelineTypeEnum.JENKINS);
        this.jobName = jobName;
        this.repositoryUrl = repositoryUrl;
        this.branch = branch;
        this.environment = environment;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public void setRepositoryUrl(String repositoryUrl) {
        this.repositoryUrl = repositoryUrl;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
