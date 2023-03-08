package com.arthor.core.env.store.mysql;

import com.arthor.core.env.store.EnvDO;
import com.arthor.core.env.store.EnvStore;
import com.arthor.core.env.store.mysql.EnvMapper;

public class MysqlEnvStore implements EnvStore {

    private final EnvMapper envMapper;

    public MysqlEnvStore(EnvMapper envMapper) {
        this.envMapper = envMapper;
    }

    @Override
    public EnvDO findById(Long id) {
        return envMapper.findById(id);
    }
}
