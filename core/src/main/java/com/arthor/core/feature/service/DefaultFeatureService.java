package com.arthor.core.feature.service;

import com.arthor.core.feature.DefaultFeature;
import com.arthor.core.feature.Feature;
import com.arthor.core.feature.model.CreateFeatureRequest;
import com.arthor.core.feature.model.ListFeatureRequest;
import com.arthor.core.feature.store.FeatureDO;
import com.arthor.core.feature.store.FeatureStore;
import com.arthor.core.common.utils.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultFeatureService implements FeatureService {

    private final FeatureStore featureStore;

    public DefaultFeatureService(FeatureStore featureStore) {
        this.featureStore = featureStore;
    }

    @Override
    public void create(CreateFeatureRequest request) {
        FeatureDO entity = new FeatureDO();
        entity.setApplicationId(request.getApplicationId());
        entity.setName(request.getFeatureName());
        entity.setApplicationName(request.getApplicationName());
        entity.setRepositoryType(request.getRepositoryTpe());
        entity.setRepositoryUrl(request.getRepositoryUrl());
        entity.setBranch(request.getBranch());
        Assert.isTrue(featureStore.create(entity), "保存功能失败");
    }

    @Override
    public Feature findById(Long id) {
        FeatureDO entity = featureStore.findById(id);
        Assert.notNull(entity, "无法获取功能信息,[ " + id + " ]");
        return convert(entity);
    }

    @Override
    public List<Feature> listByCondition(ListFeatureRequest request) {
        return featureStore.listByCondition(request).stream().map(this::convert).collect(Collectors.toList());
    }

    private Feature convert(FeatureDO entity) {
        Feature feature = new DefaultFeature();
        feature.setId(entity.getId());
        feature.setName(entity.getName());
        feature.setApplicationId(entity.getApplicationId());
        feature.setApplicationName(entity.getApplicationName());
        feature.setRepositoryType(entity.getRepositoryType());
        feature.setRepositoryUrl(entity.getRepositoryUrl());
        feature.setBranch(entity.getBranch());
        return feature;
    }

}