package com.arthor.core.deploy;

import com.arthor.core.common.enumeration.*;

import java.time.LocalDateTime;

public class DefaultDeployment implements Deployment {

    /**
     * 主键
     */
    private Long id;
    /**
     * 应用主键
     */
    private Long applicationId;
    /**
     * 应用名称
     */
    private String applicationName;
    /**
     * 功能主键
     */
    private Long featureId;
    /**
     * 构建记录主键
     */
    private Long buildRecordId;
    /**
     * 环境
     */
    private String env;
    /**
     * 命名空间
     */
    private String namespace;
    /**
     * host
     */
    private String host;
    /**
     * 部署模式
     */
    private DeployModeEnum deployMode;
    /**
     * 部署name
     */
    private String deploymentName;
    /**
     * 镜像id
     */
    private String deploymentImageId;
    /**
     * 镜像拉取策略
     */
    private String deploymentImagePullPolicy;
    /**
     * 副本数
     */
    private Integer deploymentReplicas;
    /**
     * 已更新副本数
     */
    private Integer deploymentUpdatedReplicas;
    /**
     * 准备好的副本数
     */
    private Integer deploymentReadyReplicas;
    /**
     * 可用副本数
     */
    private Integer deploymentAvailableReplicas;
    /**
     * 不可用副本数
     */
    private Integer deploymentUnavailableReplicas;
    /**
     * pod标签名称
     */
    private String deploymentPodLabelName;
    /**
     * 容器名称
     */
    private String deploymentContainerName;
    /**
     * 容器端口
     */
    private Integer deploymentContainerPort;
    /**
     * 部署扩展
     */
    private String deploymentExt;
    /**
     * pods
     */
    private String pods;
    /**
     * deployment状态
     */
    private DeployItemStatusEnum deploymentStatus;
    /**
     * 服务name
     */
    private String serviceName;
    /**
     * 服务扩展
     */
    private String serviceExt;
    /**
     * service状态
     */
    private DeployItemStatusEnum serviceStatus;
    /**
     * 路由名称
     */
    private String routeName;
    /**
     * 路由路径
     */
    private String routePath;
    /**
     * 路由扩展
     */
    private String routeExt;
    /**
     * 路由状态
     */
    private DeployItemStatusEnum routeStatus;
    /**
     * 关联的部署id
     */
    private Long relatedDeploymentId;
    /**
     * 部署限定符
     */
    private String symbol;
    /**
     * 逻辑时钟
     */
    private Long tick;
    /**
     * 外部服务标识
     */
    private Boolean external;
    /**
     * 生命周期
     */
    private LifecycleEnum lifecycle;
    /**
     * 下线时间
     */
    private LocalDateTime shutdownTime;
    /**
     * canary标识
     */
    private Boolean canary;
    /**
     * canary类型
     */
    private CanaryTypeEnum canaryType;
    /**
     * canary值对象
     */
    private String canaryValue;
    /**
     * 部署状态
     */
    private DeployStatusEnum status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 修改时间
     */
    private LocalDateTime finishTime;

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

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getBuildRecordId() {
        return buildRecordId;
    }

    public void setBuildRecordId(Long buildRecordId) {
        this.buildRecordId = buildRecordId;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public DeployModeEnum getDeployMode() {
        return deployMode;
    }

    @Override
    public void setDeployMode(DeployModeEnum deployMode) {
        this.deployMode = deployMode;
    }

    public String getDeploymentName() {
        return deploymentName;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public String getDeploymentImageId() {
        return deploymentImageId;
    }

    public void setDeploymentImageId(String deploymentImageId) {
        this.deploymentImageId = deploymentImageId;
    }

    public String getDeploymentImagePullPolicy() {
        return deploymentImagePullPolicy;
    }

    public void setDeploymentImagePullPolicy(String deploymentImagePullPolicy) {
        this.deploymentImagePullPolicy = deploymentImagePullPolicy;
    }

    public Integer getDeploymentReplicas() {
        return deploymentReplicas;
    }

    public void setDeploymentReplicas(Integer deploymentReplicas) {
        this.deploymentReplicas = deploymentReplicas;
    }

    public Integer getDeploymentUpdatedReplicas() {
        return deploymentUpdatedReplicas;
    }

    public void setDeploymentUpdatedReplicas(Integer deploymentUpdatedReplicas) {
        this.deploymentUpdatedReplicas = deploymentUpdatedReplicas;
    }

    public Integer getDeploymentReadyReplicas() {
        return deploymentReadyReplicas;
    }

    public void setDeploymentReadyReplicas(Integer deploymentReadyReplicas) {
        this.deploymentReadyReplicas = deploymentReadyReplicas;
    }

    public Integer getDeploymentAvailableReplicas() {
        return deploymentAvailableReplicas;
    }

    public void setDeploymentAvailableReplicas(Integer deploymentAvailableReplicas) {
        this.deploymentAvailableReplicas = deploymentAvailableReplicas;
    }

    public Integer getDeploymentUnavailableReplicas() {
        return deploymentUnavailableReplicas;
    }

    public void setDeploymentUnavailableReplicas(Integer deploymentUnavailableReplicas) {
        this.deploymentUnavailableReplicas = deploymentUnavailableReplicas;
    }

    public String getDeploymentPodLabelName() {
        return deploymentPodLabelName;
    }

    public void setDeploymentPodLabelName(String deploymentPodLabelName) {
        this.deploymentPodLabelName = deploymentPodLabelName;
    }

    public String getDeploymentContainerName() {
        return deploymentContainerName;
    }

    public void setDeploymentContainerName(String deploymentContainerName) {
        this.deploymentContainerName = deploymentContainerName;
    }

    public Integer getDeploymentContainerPort() {
        return deploymentContainerPort;
    }

    public void setDeploymentContainerPort(Integer deploymentContainerPort) {
        this.deploymentContainerPort = deploymentContainerPort;
    }

    public String getPods() {
        return pods;
    }

    public void setPods(String pods) {
        this.pods = pods;
    }

    @Override
    public String getDeploymentExt() {
        return deploymentExt;
    }

    @Override
    public void setDeploymentExt(String deploymentExt) {
        this.deploymentExt = deploymentExt;
    }

    public DeployItemStatusEnum getDeploymentStatus() {
        return deploymentStatus;
    }

    public void setDeploymentStatus(DeployItemStatusEnum deploymentStatus) {
        this.deploymentStatus = deploymentStatus;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    @Override
    public String getServiceExt() {
        return serviceExt;
    }

    @Override
    public void setServiceExt(String serviceExt) {
        this.serviceExt = serviceExt;
    }

    public DeployItemStatusEnum getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(DeployItemStatusEnum serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public String getRoutePath() {
        return routePath;
    }

    public void setRoutePath(String routePath) {
        this.routePath = routePath;
    }

    @Override
    public String getRouteExt() {
        return routeExt;
    }

    @Override
    public void setRouteExt(String routeExt) {
        this.routeExt = routeExt;
    }

    public DeployItemStatusEnum getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(DeployItemStatusEnum routeStatus) {
        this.routeStatus = routeStatus;
    }

    public Long getRelatedDeploymentId() {
        return relatedDeploymentId;
    }

    public void setRelatedDeploymentId(Long relatedDeploymentId) {
        this.relatedDeploymentId = relatedDeploymentId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Long getTick() {
        return tick;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }

    public Boolean getExternal() {
        return external;
    }

    public void setExternal(Boolean external) {
        this.external = external;
    }

    public LifecycleEnum getLifecycle() {
        return lifecycle;
    }

    public void setLifecycle(LifecycleEnum lifecycle) {
        this.lifecycle = lifecycle;
    }

    public LocalDateTime getShutdownTime() {
        return shutdownTime;
    }

    public void setShutdownTime(LocalDateTime shutdownTime) {
        this.shutdownTime = shutdownTime;
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

    public DeployStatusEnum getStatus() {
        return status;
    }

    public void setStatus(DeployStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(LocalDateTime updateTime) {
        this.finishTime = updateTime;
    }

}
