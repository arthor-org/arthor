package com.arthor.core.pipeline.model;

public class CreatePipelineRequest {

    /**
     * 流水线名称
     */
    private String jobName;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
}
