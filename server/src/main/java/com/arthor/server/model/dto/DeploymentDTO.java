package com.arthor.server.model.dto;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.arthor.core.common.enumeration.*;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.Pod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeploymentDTO {

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
    private JSONObject deploymentExt;
    /**
     * deployment状态
     */
    private DeployItemStatusEnum deploymentStatus;
    /**
     * pods
     */
    private List<Pod> pods;
    /**
     * 服务name
     */
    private String serviceName;
    /**
     * 部署扩展
     */
    private JSONObject serviceExt;
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
    private JSONObject routeExt;
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

    public static DeploymentDTO valueOf(Deployment deployment) {
        if (Objects.isNull(deployment)) {
            return null;
        }
        return DeploymentDTO.builder().id(deployment.getId()).applicationId(deployment.getApplicationId())
                .applicationName(deployment.getApplicationName()).featureId(deployment.getFeatureId())
                .tick(deployment.getTick()).buildRecordId(deployment.getBuildRecordId()).env(deployment.getEnv())
                .namespace(deployment.getNamespace()).host(deployment.getHost()).deployMode(deployment.getDeployMode())
                .deploymentName(deployment.getDeploymentName()).deploymentImageId(deployment.getDeploymentImageId())
                .deploymentImagePullPolicy(deployment.getDeploymentImagePullPolicy())
                .deploymentReplicas(deployment.getDeploymentReplicas())
                .deploymentUpdatedReplicas(deployment.getDeploymentUpdatedReplicas())
                .deploymentAvailableReplicas(deployment.getDeploymentAvailableReplicas())
                .deploymentUnavailableReplicas(deployment.getDeploymentUnavailableReplicas())
                .deploymentReadyReplicas(deployment.getDeploymentReadyReplicas())
                .deploymentPodLabelName(deployment.getDeploymentPodLabelName())
                .deploymentContainerName(deployment.getDeploymentContainerName())
                .deploymentContainerPort(deployment.getDeploymentContainerPort())
                .deploymentExt(StringUtils.isNotBlank(deployment.getDeploymentExt()) ?
                        JSON.parseObject(deployment.getDeploymentExt()) : null)
                .pods(StringUtils.isNotBlank(deployment.getPods()) ?
                        JSON.parseArray(deployment.getPods(), Pod.class) : null)
                .deploymentStatus(deployment.getDeploymentStatus())
                .lifecycle(deployment.getLifecycle()).shutdownTime(deployment.getShutdownTime())
                .relatedDeploymentId(deployment.getRelatedDeploymentId())
                .external(deployment.getExternal()).symbol(deployment.getSymbol())
                .canary(deployment.getCanary()).canaryType(deployment.getCanaryType())
                .canaryValue(deployment.getCanaryValue())

                .serviceName(deployment.getServiceName())
                .serviceExt(StringUtils.isNotBlank(deployment.getServiceExt()) ?
                        JSON.parseObject(deployment.getServiceExt()) : null)
                .serviceStatus(deployment.getServiceStatus())

                .routeName(deployment.getRouteName())
                .routeExt(StringUtils.isNotBlank(deployment.getRouteExt()) ?
                        JSON.parseObject(deployment.getRouteExt()) : null)
                .routePath(deployment.getRoutePath())
                .routeStatus(deployment.getRouteStatus()).status(deployment.getStatus())

                .createTime(deployment.getCreateTime()).finishTime(deployment.getFinishTime()).build();
    }

}
