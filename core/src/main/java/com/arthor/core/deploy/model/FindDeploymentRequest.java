package com.arthor.core.deploy.model;

import com.arthor.core.common.enumeration.DeployStatusEnum;

public class FindDeploymentRequest {

    /**
     * 部署id
     */
    private Long id;
    /**
     * 应用id
     */
    private Long applicationId;
    /**
     * 部署symbol
     */
    private String symbol;
    /**
     * 部署namespace
     */
    private String namespace;
    /**
     * canary标识
     */
    private Boolean canary;
    /**
     * 部署状态
     */
    private DeployStatusEnum status;

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

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public Boolean getCanary() {
        return canary;
    }

    public void setCanary(Boolean canary) {
        this.canary = canary;
    }

    public DeployStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DeployStatusEnum status) {
        this.status = status;
    }
}
