package com.arthor.core.feature.store.mysql;

import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.feature.model.ListFeatureRequest;
import com.arthor.core.feature.store.FeatureDO;
import com.arthor.core.feature.store.FeatureStore;
import com.arthor.core.feature.store.mysql.FeatureMapper;

import java.util.List;

public class MysqlFeatureStore implements FeatureStore {

    private final FeatureMapper featureMapper;

    public MysqlFeatureStore(FeatureMapper featureMapper) {
        this.featureMapper = featureMapper;
    }

    @Override
    public Boolean create(FeatureDO entity) {
        return SqlHelper.retBool(featureMapper.save(entity));
    }

    @Override
    public FeatureDO findById(Long id) {
        return featureMapper.findById(id);
    }

    @Override
    public List<FeatureDO> listByCondition(ListFeatureRequest request) {
        return featureMapper.list(request);
    }

}
