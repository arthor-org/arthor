package com.arthor.core.integration.pipeline;

public interface PipelineProperties {

    String getEndPoint();

    void setEndPoint(String endPoint);

    String getUsername();

    void setUsername(String username);

    String getApiToken();

    void setApiToken(String apiToken);

    String getImageRepositoryHost();

    void setImageRepositoryHost(String imageRepositoryHost);

    String getImageRepositoryOrg();

    void setImageRepositoryOrg(String imageRepositoryOrg);

    String getImageRepositoryUsername();

    void setImageRepositoryUsername(String imageRepositoryUsername);

    String getImageRepositoryPassword();

    void setImageRepositoryPassword(String imageRepositoryPassword);

}
