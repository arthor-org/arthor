package com.arthor.core.deploy.model;

import com.arthor.core.common.enumeration.DeployStatusEnum;

import java.util.List;

public class ListDeploymentRequest {
    /**
     * 构建id
     */
    private Long buildId;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 部署状态
     */
    private List<DeployStatusEnum> statusList;
    /**
     * 部署限定符
     */
    private String symbol;
    /**
     * canary标识
     */
    private Boolean canary;
    /**
     * 逻辑时钟阈值
     */
    private Long tickThreshold;

    public Long getBuildId() {
        return buildId;
    }

    public void setBuildId(Long buildId) {
        this.buildId = buildId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public List<DeployStatusEnum> getStatusList() {
        return statusList;
    }

    public void setStatusList(List<DeployStatusEnum> statusList) {
        this.statusList = statusList;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getCanary() {
        return canary;
    }

    public void setCanary(Boolean canary) {
        this.canary = canary;
    }

    public Long getTickThreshold() {
        return tickThreshold;
    }

    public void setTickThreshold(Long tickThreshold) {
        this.tickThreshold = tickThreshold;
    }
}
