package com.arthor.core.build.model;

import com.arthor.core.common.enumeration.BuildRecordStatusEnum;

public class ListBuildRecordRequest {

    /**
     * 环境id
     */
    private Long envId;
    /**
     * 功能id
     */
    private Long featureId;
    /**
     * 构建记录状态
     */
    private BuildRecordStatusEnum status;

    public Long getEnvId() {
        return envId;
    }

    public void setEnvId(Long envId) {
        this.envId = envId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public BuildRecordStatusEnum getStatus() {
        return status;
    }

    public void setStatus(BuildRecordStatusEnum status) {
        this.status = status;
    }
}
