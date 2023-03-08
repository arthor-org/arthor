package com.arthor.core.deploy.store.mysql;

import com.arthor.core.deploy.store.DeployRecordDO;

public interface DeployRecordMapper {

    int save(DeployRecordDO entity);

}
