package com.arthor.core.feature.model;

import com.arthor.core.common.enumeration.RepositoryTypeEnum;

public class CreateFeatureRequest {

    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 功能名称
     */
    private String featureName;
    /**
     * 仓库类型
     */
    private RepositoryTypeEnum repositoryTpe;
    /**
     * 仓库地址
     */
    private String repositoryUrl;
    /**
     * 分支
     */
    private String branch;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    public RepositoryTypeEnum getRepositoryTpe() {
        return repositoryTpe;
    }

    public void setRepositoryTpe(RepositoryTypeEnum repositoryTpe) {
        this.repositoryTpe = repositoryTpe;
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
}
