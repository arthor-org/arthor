package com.arthor.core.command.request;

import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.OperateEnum;

public class CreateDeployCommandRequest extends AbstractDeployCommandRequest {

    public CreateDeployCommandRequest(OperateEnum operate) {
        super(operate);
    }

    /**
     * 构建id
     */
    private Long buildRecordId;
    /**
     * 副本数
     */
    private Integer replicas;
    /**
     * 部署限定符,未拼接applicationName的
     */
    private String symbol;
    /**
     * 外部服务标识
     */
    private Boolean external;
    /**
     * 镜像拉取策略
     */
    private String imagePullPolicy;
    /**
     * pod端口
     */
    private Integer podPort;
    private Boolean canary;
    private CanaryTypeEnum canaryType;
    private String canaryValue;

    public Long getBuildRecordId() {
        return buildRecordId;
    }

    public void setBuildRecordId(Long buildRecordId) {
        this.buildRecordId = buildRecordId;
    }

    public Integer getReplicas() {
        return replicas;
    }

    public void setReplicas(Integer replicas) {
        this.replicas = replicas;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Boolean getExternal() {
        return external;
    }

    public void setExternal(Boolean external) {
        this.external = external;
    }

    public String getImagePullPolicy() {
        return imagePullPolicy;
    }

    public void setImagePullPolicy(String imagePullPolicy) {
        this.imagePullPolicy = imagePullPolicy;
    }

    public Integer getPodPort() {
        return podPort;
    }

    public void setPodPort(Integer podPort) {
        this.podPort = podPort;
    }

    public Boolean getCanary() {
        return canary;
    }

    public void setCanary(Boolean canary) {
        this.canary = canary;
    }

    public CanaryTypeEnum getCanaryType() {
        return canaryType;
    }

    public void setCanaryType(CanaryTypeEnum canaryType) {
        this.canaryType = canaryType;
    }

    public String getCanaryValue() {
        return canaryValue;
    }

    public void setCanaryValue(String canaryValue) {
        this.canaryValue = canaryValue;
    }

}
