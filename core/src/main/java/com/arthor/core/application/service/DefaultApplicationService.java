package com.arthor.core.application.service;

import com.arthor.core.application.Application;
import com.arthor.core.application.DefaultApplication;
import com.arthor.core.application.model.CreateApplicationRequest;
import com.arthor.core.application.model.ListApplicationRequest;
import com.arthor.core.application.store.ApplicationDO;
import com.arthor.core.application.store.ApplicationStore;
import com.arthor.core.common.utils.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultApplicationService implements ApplicationService {

    private final ApplicationStore applicationStore;

    public DefaultApplicationService(ApplicationStore applicationStore) {
        this.applicationStore = applicationStore;
    }

    @Override
    public void create(CreateApplicationRequest request) {
        ApplicationDO entity = new ApplicationDO();
        entity.setName(request.getName());
        entity.setDescription(request.getDescription());
        entity.setPipelineId(request.getPipelineId());
        Assert.isTrue(applicationStore.create(entity), "保存应用失败");
    }

    @Override
    public List<Application> list(ListApplicationRequest request) {
        return applicationStore.list(request).stream()
                .map(this::convert).collect(Collectors.toList());
    }

    @Override
    public Application findById(Long id) {
        ApplicationDO entity = applicationStore.findById(id);
        Assert.notNull(entity, "无法获取应用信息,[ " + id + " ]");
        return convert(entity);
    }

    private Application convert(ApplicationDO entity) {
        Application application = new DefaultApplication();
        application.setId(entity.getId());
        application.setName(entity.getName());
        application.setDescription(entity.getDescription());
        application.setPipelineId(entity.getPipelineId());
        return application;
    }

}
