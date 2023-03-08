package com.arthor.core.registry;

import com.alibaba.fastjson.JSONObject;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.LifecycleEnum;

import java.time.LocalDateTime;

public class DefaultMetadata implements Metadata {

    private String name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getSymbol() {
        return symbol;
    }

    @Override
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String getGroup() {
        return group;
    }

    @Override
    public void setGroup(String group) {
        this.group = group;
    }

    @Override
    public String getIp() {
        return ip;
    }

    @Override
    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public LifecycleEnum getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(LifecycleEnum lifecycle) {
        this.lifecycle = lifecycle;
    }

    @Override
    public boolean isCanary() {
        return canary;
    }

    @Override
    public void setCanary(boolean canary) {
        this.canary = canary;
    }

    public CanaryTypeEnum getCanaryType() {
        return canaryType;
    }

    public void setCanaryType(CanaryTypeEnum canaryType) {
        this.canaryType = canaryType;
    }

    @Override
    public String getCanaryValue() {
        return canaryValue;
    }

    @Override
    public void setCanaryValue(String canaryValue) {
        this.canaryValue = canaryValue;
    }

    @Override
    public LocalDateTime getLatestTime() {
        return latestTime;
    }

    @Override
    public void setLatestTime(LocalDateTime latestTime) {
        this.latestTime = latestTime;
    }

    @Override
    public JSONObject getFuture() {
        return future;
    }

    @Override
    public void setFuture(JSONObject future) {
        this.future = future;
    }
}
