package com.arthor.core.deploy;

import com.arthor.core.common.enumeration.*;

import java.time.LocalDateTime;

public interface Deployment {
    Long getId();

    void setId(Long id);

    Long getApplicationId();

    void setApplicationId(Long applicationId);

    String getApplicationName();

    void setApplicationName(String applicationName);

    Long getFeatureId();

    void setFeatureId(Long featureId);

    Long getBuildRecordId();

    void setBuildRecordId(Long buildRecordId);

    String getEnv();

    void setEnv(String env);

    String getNamespace();

    void setNamespace(String namespace);

    String getHost();

    void setHost(String host);

    DeployModeEnum getDeployMode();

    void setDeployMode(DeployModeEnum deployMode);

    String getDeploymentName();

    void setDeploymentName(String deploymentName);

    String getDeploymentImageId();

    void setDeploymentImageId(String deploymentImageId);

    String getDeploymentImagePullPolicy();

    void setDeploymentImagePullPolicy(String deploymentImagePullPolicy);

    Integer getDeploymentReplicas();

    void setDeploymentReplicas(Integer deploymentReplicas);

    Integer getDeploymentUpdatedReplicas();

    void setDeploymentUpdatedReplicas(Integer deploymentUpdatedReplicas);

    Integer getDeploymentReadyReplicas();

    void setDeploymentReadyReplicas(Integer deploymentReadyReplicas);

    Integer getDeploymentAvailableReplicas();

    void setDeploymentAvailableReplicas(Integer deploymentAvailableReplicas);

    Integer getDeploymentUnavailableReplicas();

    void setDeploymentUnavailableReplicas(Integer deploymentUnavailableReplicas);

    String getDeploymentPodLabelName();

    void setDeploymentPodLabelName(String deploymentPodLabelName);

    String getDeploymentContainerName();

    void setDeploymentContainerName(String deploymentContainerName);

    Integer getDeploymentContainerPort();

    void setDeploymentContainerPort(Integer deploymentContainerPort);

    String getPods();

    void setPods(String pods);

    String getDeploymentExt();

    void setDeploymentExt(String deploymentExt);

    DeployItemStatusEnum getDeploymentStatus();

    void setDeploymentStatus(DeployItemStatusEnum deploymentStatus);

    String getServiceName();

    void setServiceName(String serviceName);

    String getServiceExt();

    void setServiceExt(String serviceExt);

    DeployItemStatusEnum getServiceStatus();

    void setServiceStatus(DeployItemStatusEnum serviceStatus);

    String getRouteName();

    void setRouteName(String routeName);

    String getRoutePath();

    void setRoutePath(String routePath);

    String getRouteExt();

    void setRouteExt(String routeExt);

    DeployItemStatusEnum getRouteStatus();

    void setRouteStatus(DeployItemStatusEnum routeStatus);

    Long getRelatedDeploymentId();

    void setRelatedDeploymentId(Long relatedDeploymentId);

    String getSymbol();

    void setSymbol(String symbol);

    Long getTick();

    void setTick(Long tick);

    Boolean getExternal();

    void setExternal(Boolean external);

    LifecycleEnum getLifecycle();

    void setLifecycle(LifecycleEnum lifecycle);

    LocalDateTime getShutdownTime();

    void setShutdownTime(LocalDateTime shutdownTime);

    Boolean getCanary();

    void setCanary(Boolean canary);

    CanaryTypeEnum getCanaryType();

    void setCanaryType(CanaryTypeEnum canaryType);

    String getCanaryValue();

    void setCanaryValue(String canaryValue);

    DeployStatusEnum getStatus();

    void setStatus(DeployStatusEnum status);

    LocalDateTime getCreateTime();

    void setCreateTime(LocalDateTime createTime);

    LocalDateTime getFinishTime();

    void setFinishTime(LocalDateTime updateTime);
    
}
