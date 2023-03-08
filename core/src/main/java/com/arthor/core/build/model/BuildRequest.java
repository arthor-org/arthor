package com.arthor.core.build.model;


public class BuildRequest {

    /**
     * 功能id
     */
    private Long featureId;
    /**
     * 环境id
     */
    private Long envId;

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }
}
