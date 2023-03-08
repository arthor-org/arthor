package com.arthor.core.integration.repository.gitlab;

import com.arthor.core.integration.repository.RepositoryProperties;

public class GitlabProperties implements RepositoryProperties {

    private String accessTokens;

    private String host;

    public String getAccessTokens() {
        return accessTokens;
    }

    public void setAccessTokens(String accessTokens) {
        this.accessTokens = accessTokens;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }
}
