package com.arthor.core.deploy.service;

import com.arthor.core.deploy.DefaultDeployment;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.deploy.store.DeploymentDO;
import com.arthor.core.deploy.store.DeploymentStore;
import com.arthor.core.common.utils.Assert;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DefaultDeploymentService implements DeploymentService {

    private final DeploymentStore deploymentStore;
    public DefaultDeploymentService(DeploymentStore deploymentStore) {
        this.deploymentStore = deploymentStore;
    }

    @Override
    public Deployment findById(Long id) {
        DeploymentDO entity = deploymentStore.findById(id);
        Assert.notNull(entity, "无法获取部署信息,[ " + id + " ]");
        return convert(entity);
    }

    @Override
    public List<Deployment> listByCondition(ListDeploymentRequest request) {
        return deploymentStore.list(request).stream()
                .map(this::convert).collect(Collectors.toList());
    }

    @Override
    public void updateDeployment(CreateDeploymentRequest request) {
        DeploymentDO entity = new DeploymentDO();
        entity.setId(request.getId());
        entity.setTick(request.getTick());
        entity.setCanary(request.getCanary());
        entity.setCanaryType(request.getCanaryType());
        entity.setCanaryValue(request.getCanaryValue());
        entity.setStatus(request.getStatus());
        entity.setDeploymentStatus(request.getDeploymentStatus());
        entity.setServiceStatus(request.getServiceStatus());
        entity.setRouteStatus(request.getRouteStatus());
        entity.setLifecycle(request.getLifecycle());
        entity.setShutdownTime(request.getShutdownTime());
        entity.setPods(request.getPods());
        entity.setDeploymentReadyReplicas(request.getDeploymentReadyReplicas());
        entity.setDeploymentUpdatedReplicas(request.getDeploymentUpdatedReplicas());
        entity.setDeploymentAvailableReplicas(request.getDeploymentAvailableReplicas());
        entity.setDeploymentUnavailableReplicas(request.getDeploymentUnavailableReplicas());
        entity.setRelatedDeploymentId(request.getRelatedDeploymentId());
        Assert.isTrue(deploymentStore.update(entity), "修改部署失败["+ request.getId() +"]");
    }

    @Override
    public Deployment createDeployment(CreateDeploymentRequest request) {
        DeploymentDO entity = new DeploymentDO();
        entity.setApplicationId(request.getApplicationId());
        entity.setApplicationName(request.getApplicationName());
        entity.setFeatureId(request.getFeatureId());
        entity.setBuildRecordId(request.getBuildRecordId());
        entity.setEnv(request.getEnv());
        entity.setNamespace(request.getNamespace());
        entity.setHost(request.getHost());
        entity.setDeploymentName(request.getDeploymentName());
        entity.setDeploymentImageId(request.getDeploymentImageId());
        entity.setDeploymentImagePullPolicy(request.getDeploymentImagePullPolicy());
        entity.setDeploymentReplicas(request.getDeploymentReplicas());
        entity.setDeploymentPodLabelName(request.getDeploymentPodLabelName());
        entity.setDeploymentContainerName(request.getDeploymentContainerName());
        entity.setDeploymentContainerPort(request.getDeploymentContainerPort());
        entity.setDeploymentStatus(request.getDeploymentStatus());
        entity.setServiceName(request.getServiceName());
        entity.setServiceExt(request.getServiceExt());
        entity.setRouteExt(request.getRouteExt());
        entity.setDeploymentExt(request.getDeploymentExt());
        entity.setServiceStatus(request.getServiceStatus());
        entity.setRouteName(request.getRouteName());
        entity.setRoutePath(request.getRoutePath());
        entity.setRouteStatus(request.getRouteStatus());
        entity.setSymbol(request.getSymbol());
        entity.setTick(request.getTick());
        entity.setExternal(request.getExternal());
        entity.setLifecycle(request.getLifecycle());
        entity.setCanary(request.getCanary());
        entity.setCanaryType(request.getCanaryType());
        entity.setCanaryValue(request.getCanaryValue());
        entity.setStatus(request.getStatus());
        entity.setCreateTime(request.getCreateTime());
        entity.setDeployMode(request.getDeployMode());
        Assert.isTrue(deploymentStore.save(entity), "保存部署失败");
        return convert(entity);
    }

    private Deployment convert(DeploymentDO entity) {
        if (Objects.isNull(entity)) {
            return null;
        }
        Deployment deployment = new DefaultDeployment();
        deployment.setId(entity.getId());
        deployment.setApplicationId(entity.getApplicationId());
        deployment.setApplicationName(entity.getApplicationName());
        deployment.setFeatureId(entity.getFeatureId());
        deployment.setTick(entity.getTick());
        deployment.setBuildRecordId(entity.getBuildRecordId());
        deployment.setEnv(entity.getEnv());
        deployment.setNamespace(entity.getNamespace());
        deployment.setHost(entity.getHost());
        deployment.setDeploymentName(entity.getDeploymentName());
        deployment.setDeploymentImageId(entity.getDeploymentImageId());
        deployment.setDeploymentImagePullPolicy(entity.getDeploymentImagePullPolicy());
        deployment.setDeploymentReplicas(entity.getDeploymentReplicas());
        deployment.setDeploymentUpdatedReplicas(entity.getDeploymentUpdatedReplicas());
        deployment.setDeploymentReadyReplicas(entity.getDeploymentReadyReplicas());
        deployment.setDeploymentAvailableReplicas(entity.getDeploymentAvailableReplicas());
        deployment.setDeploymentUnavailableReplicas(entity.getDeploymentUnavailableReplicas());
        deployment.setDeploymentPodLabelName(entity.getDeploymentPodLabelName());
        deployment.setDeploymentContainerName(entity.getDeploymentContainerName());
        deployment.setDeploymentContainerPort(entity.getDeploymentContainerPort());
        deployment.setDeploymentStatus(entity.getDeploymentStatus());
        deployment.setServiceStatus(entity.getServiceStatus());
        deployment.setDeploymentStatus(entity.getDeploymentStatus());
        deployment.setRouteStatus(entity.getRouteStatus());
        deployment.setLifecycle(entity.getLifecycle());
        deployment.setShutdownTime(entity.getShutdownTime());
        deployment.setServiceName(entity.getServiceName());
        deployment.setDeploymentExt(entity.getDeploymentExt());
        deployment.setServiceExt(entity.getServiceExt());
        deployment.setRouteExt(entity.getRouteExt());
        deployment.setRelatedDeploymentId(entity.getRelatedDeploymentId());
        deployment.setRouteName(entity.getRouteName());
        deployment.setDeployMode(entity.getDeployMode());
        deployment.setRoutePath(entity.getRoutePath());
        deployment.setSymbol(entity.getSymbol());
        deployment.setExternal(entity.getExternal());
        deployment.setCanary(entity.getCanary());
        deployment.setCanaryType(entity.getCanaryType());
        deployment.setCanaryValue(entity.getCanaryValue());
        deployment.setStatus(entity.getStatus());
        deployment.setPods(entity.getPods());
        return deployment;
    }

}
