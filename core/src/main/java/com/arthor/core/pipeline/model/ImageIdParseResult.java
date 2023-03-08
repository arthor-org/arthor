package com.arthor.core.pipeline.model;

public class ImageIdParseResult {

    /**
     * 流水线名称
     */
    private String jobName;
    /**
     * git提交id
     */
    private String commitId;

    public ImageIdParseResult() {

    }
    public ImageIdParseResult(String jobName, String commitId) {
        this.jobName = jobName;
        this.commitId = commitId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }
}
