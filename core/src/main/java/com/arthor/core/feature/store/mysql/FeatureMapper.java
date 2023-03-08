package com.arthor.core.feature.store.mysql;

import com.arthor.core.feature.model.ListFeatureRequest;
import com.arthor.core.feature.store.FeatureDO;

import java.util.List;

public interface FeatureMapper {

    int save(FeatureDO featureDO);

    FeatureDO findById(Long id);

    List<FeatureDO> list(ListFeatureRequest request);
}
