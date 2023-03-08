package com.arthor.server.facade.impl;

import com.arthor.core.application.Application;
import com.arthor.core.application.service.ApplicationService;
import com.arthor.core.feature.model.CreateFeatureRequest;
import com.arthor.core.feature.model.ListFeatureRequest;
import com.arthor.server.facade.FeatureFacade;
import com.arthor.server.model.dto.FeatureDTO;
import com.arthor.server.model.param.feature.ListFeatureParam;
import com.arthor.server.model.param.feature.CreateFeatureParam;
import com.arthor.core.feature.service.FeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeatureFacadeImpl implements FeatureFacade {

    private final ApplicationService applicationService;
    private final FeatureService featureService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean createFeature(CreateFeatureParam param) {
        Application application = applicationService.findById(param.getApplicationId());
        CreateFeatureRequest createFeatureRequest = new CreateFeatureRequest();
        createFeatureRequest.setApplicationId(application.getId());
        createFeatureRequest.setApplicationName(application.getName());
        createFeatureRequest.setFeatureName(param.getFeatureName());
        createFeatureRequest.setRepositoryTpe(param.getRepositoryTpe());
        createFeatureRequest.setRepositoryUrl(param.getRepositoryUrl());
        createFeatureRequest.setBranch(param.getBranch());
        featureService.create(createFeatureRequest);
        return Boolean.TRUE;
    }

    @Override
    public List<FeatureDTO> listByCondition(ListFeatureParam param) {
        ListFeatureRequest listFeatureRequest = new ListFeatureRequest();
        listFeatureRequest.setApplicationId(param.getApplicationId());
        return featureService.listByCondition(listFeatureRequest).stream()
                .map(FeatureDTO::valueOf).collect(Collectors.toList());
    }

}
