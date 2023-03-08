package com.arthor.core.feature.service;

import com.arthor.core.feature.Feature;
import com.arthor.core.feature.model.CreateFeatureRequest;
import com.arthor.core.feature.model.ListFeatureRequest;

import java.util.List;

public interface FeatureService {

    /**
     * 功能保存/修改
     *
     * @param request
     * @return
     */
    void create(CreateFeatureRequest request);

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    Feature findById(Long id);

    /**
     * 功能列表
     *
     * @param request
     * @return
     */
    List<Feature> listByCondition(ListFeatureRequest request);

}
