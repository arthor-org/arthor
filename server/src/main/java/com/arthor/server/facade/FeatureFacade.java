package com.arthor.server.facade;

import com.arthor.server.model.dto.FeatureDTO;
import com.arthor.server.model.param.feature.ListFeatureParam;
import com.arthor.server.model.param.feature.CreateFeatureParam;

import java.util.List;

public interface FeatureFacade {
    Boolean createFeature(CreateFeatureParam param);

    List<FeatureDTO> listByCondition(ListFeatureParam param);

}
