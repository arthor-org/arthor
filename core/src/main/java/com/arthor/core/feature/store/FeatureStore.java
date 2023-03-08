package com.arthor.core.feature.store;

import com.arthor.core.feature.model.ListFeatureRequest;

import java.util.List;

public interface FeatureStore {

    /**
     * 功能保存/修改
     *
     * @param entity
     * @return
     */
    Boolean create(FeatureDO entity);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    FeatureDO findById(Long id);

    /**
     * 功能列表
     *
     * @param request
     * @return
     */
    List<FeatureDO> listByCondition(ListFeatureRequest request);

}
