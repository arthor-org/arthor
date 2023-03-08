package com.arthor.core.application.store;

import com.arthor.core.application.model.ListApplicationRequest;

import java.util.List;

public interface ApplicationStore {

    Boolean create(ApplicationDO entity);

    List<ApplicationDO> list(ListApplicationRequest request);

    ApplicationDO findById(Long id);

}
