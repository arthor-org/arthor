package com.arthor.core.deploy.store.mysql;

import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.deploy.store.DeployRecordDO;
import com.arthor.core.deploy.store.DeployRecordStore;
import com.arthor.core.deploy.store.mysql.DeployRecordMapper;

public class MysqlDeployRecordStore implements DeployRecordStore {

    private final DeployRecordMapper deployRecordMapper;

    public MysqlDeployRecordStore(DeployRecordMapper deployRecordMapper) {
        this.deployRecordMapper = deployRecordMapper;
    }

    @Override
    public Boolean create(DeployRecordDO entity) {
        return SqlHelper.retBool(deployRecordMapper.save(entity));
    }
}
