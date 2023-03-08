package com.arthor.core.application.store.mysql;

import com.arthor.core.application.model.ListApplicationRequest;
import com.arthor.core.application.store.ApplicationDO;
import com.arthor.core.application.store.ApplicationStore;
import com.arthor.core.common.utils.SqlHelper;

import java.util.List;

public class MysqlApplicationStore implements ApplicationStore {

    private final ApplicationMapper applicationMapper;

    public MysqlApplicationStore(ApplicationMapper applicationMapper) {
        this.applicationMapper = applicationMapper;
    }

    @Override
    public Boolean create(ApplicationDO entity) {
        return SqlHelper.retBool(applicationMapper.save(entity));
    }

    @Override
    public List<ApplicationDO> list(ListApplicationRequest request) {
        return applicationMapper.list(request);
    }

    @Override
    public ApplicationDO findById(Long id) {
        return applicationMapper.findById(id);
    }
}
