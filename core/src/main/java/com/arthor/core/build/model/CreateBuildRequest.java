package com.arthor.core.build.model;


import com.arthor.core.common.enumeration.BuildRecordStatusEnum;

import java.time.LocalDateTime;

public class CreateBuildRequest {

    /**
     * 应用主键
     */
    private Long applicationId;
    /**
     * 功能主键
     */
    private Long featureId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 功能名称
     */
    private String featureName;
    /**
     * 构建编号
     */
    private String buildNumber;
    /**
     * 环境id
     */
    private Long envId;
    /**
     * 流水线job名称
     */
    private String jobName;
    /**
     * 构建状态
     */
    private BuildRecordStatusEnum status;
    /**
     * 检查次数「超过限制则直接失败」
     */
    private Integer numberOfCheck;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public BuildRecordStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BuildRecordStatusEnum status) {
        this.status = status;
    }

    public Integer getNumberOfCheck() {
        return numberOfCheck;
    }

    public void setNumberOfCheck(Integer numberOfCheck) {
        this.numberOfCheck = numberOfCheck;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}
