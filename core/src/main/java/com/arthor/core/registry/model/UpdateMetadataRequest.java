package com.arthor.core.registry.model;

import com.alibaba.fastjson.JSONObject;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.LifecycleEnum;

import java.time.LocalDateTime;

public class UpdateMetadataRequest {

    private String name;
    private String deploymentName;
    private String symbol;
    private String group;
    private String ip;
    private Integer port;
    private LifecycleEnum lifecycle;
    private boolean canary;
    private CanaryTypeEnum canaryType;
    private String canaryValue;
    private LocalDateTime latestTime;
    private JSONObject future;

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public boolean isCanary() {
        return canary;
    }

    public void setCanary(boolean canary) {
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

    public LocalDateTime getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(LocalDateTime latestTime) {
        this.latestTime = latestTime;
    }

    public LifecycleEnum getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(LifecycleEnum lifecycle) {
        this.lifecycle = lifecycle;
    }

    public JSONObject getFuture() {
        return future;
    }

    public void setFuture(JSONObject future) {
        this.future = future;
    }

}