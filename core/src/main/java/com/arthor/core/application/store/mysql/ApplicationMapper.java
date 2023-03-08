package com.arthor.core.application.store.mysql;

import com.arthor.core.application.model.ListApplicationRequest;
import com.arthor.core.application.store.ApplicationDO;

import java.util.List;

public interface ApplicationMapper {

    int save(ApplicationDO applicationDO);

    List<ApplicationDO> list(ListApplicationRequest request);

    ApplicationDO findById(Long id);

}
