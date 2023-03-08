package com.arthor.core.build.store;

import com.arthor.core.common.enumeration.BuildRecordStatusEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BuildRecordDO implements Serializable {

    /**
     * 构建记录主键
     */
    private Long id;
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
     * 本次构建对应的提交id
     */
    private String commitId;
    /**
     * 构建完成的镜像id
     */
    private String imageId;
    /**
     * 构建编号
     */
    private String buildNumber;
    /**
     * 流水线名称
     */
    private String jobName;
    /**
     * 环境id
     */
    private Long envId;
    /**
     * 构建状态「BUILDING构建中,SUCCESS构建成功,FAILURE构建失败」
     */
    private BuildRecordStatusEnum status;
    /**
     * 检查次数
     */
    private Integer numberOfCheck;
    /**
     * 构建时间
     */
    private LocalDateTime createTime;
    /**
     * 构建完成时间
     */
    private LocalDateTime finishTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getBuildNumber() {
        return buildNumber;
    }

    public void setBuildNumber(String buildNumber) {
        this.buildNumber = buildNumber;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
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

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime finishTime) {
        this.finishTime = finishTime;
    }
}
