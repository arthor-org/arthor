package com.arthor.core.registry;

import com.alibaba.fastjson.JSONObject;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.LifecycleEnum;

import java.time.LocalDateTime;

public interface Metadata {
    
    String getName();

    void setName(String name);

    String getSymbol();

    void setSymbol(String symbol);

    String getGroup();

    void setGroup(String group);

    String getIp();

    void setIp(String ip);

    Integer getPort();

    void setPort(Integer port);

    LifecycleEnum getLifecycle();

    void setLifecycle(LifecycleEnum lifecycle);

    boolean isCanary();

    void setCanary(boolean canary);

    CanaryTypeEnum getCanaryType();

    void setCanaryType(CanaryTypeEnum canaryType);

    String getCanaryValue();

    void setCanaryValue(String canaryValue);

    LocalDateTime getLatestTime();

    void setLatestTime(LocalDateTime latestTime);

    JSONObject getFuture();

    void setFuture(JSONObject future);
}
