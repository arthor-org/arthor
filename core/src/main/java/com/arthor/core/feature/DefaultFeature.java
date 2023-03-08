package com.arthor.core.feature;

import com.arthor.core.common.enumeration.RepositoryTypeEnum;

public class DefaultFeature implements Feature {

    /**
     * id
     */
    private Long id;
    /**
     * 功能名称
     */
    private String name;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 仓库类型
     */
    private RepositoryTypeEnum repositoryType;
    /**
     * 仓库地址
     */
    private String repositoryUrl;
    /**
     * 分支
     */
    private String branch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public RepositoryTypeEnum getRepositoryType() {
        return repositoryType;
    }

    public void setRepositoryType(RepositoryTypeEnum repositoryType) {
        this.repositoryType = repositoryType;
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
