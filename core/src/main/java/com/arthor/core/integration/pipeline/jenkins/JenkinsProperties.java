package com.arthor.core.integration.pipeline.jenkins;

import com.arthor.core.integration.pipeline.PipelineProperties;

public class JenkinsProperties implements PipelineProperties {

    private String folderPath;
    private String endPoint;
    private String username;
    private String apiToken;

    private String imageRepositoryHost;
    private String imageRepositoryOrg;
    private String imageRepositoryUsername;
    private String imageRepositoryPassword;

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    @Override
    public String getEndPoint() {
        return endPoint;
    }

    @Override
    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getApiToken() {
        return apiToken;
    }

    @Override
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public String getImageRepositoryHost() {
        return imageRepositoryHost;
    }

    @Override
    public void setImageRepositoryHost(String imageRepositoryHost) {
        this.imageRepositoryHost = imageRepositoryHost;
    }

    @Override
    public String getImageRepositoryOrg() {
        return imageRepositoryOrg;
    }

    @Override
    public void setImageRepositoryOrg(String imageRepositoryOrg) {
        this.imageRepositoryOrg = imageRepositoryOrg;
    }

    @Override
    public String getImageRepositoryUsername() {
        return imageRepositoryUsername;
    }

    @Override
    public void setImageRepositoryUsername(String imageRepositoryUsername) {
        this.imageRepositoryUsername = imageRepositoryUsername;
    }

    @Override
    public String getImageRepositoryPassword() {
        return imageRepositoryPassword;
    }

    @Override
    public void setImageRepositoryPassword(String imageRepositoryPassword) {
        this.imageRepositoryPassword = imageRepositoryPassword;
    }
}
