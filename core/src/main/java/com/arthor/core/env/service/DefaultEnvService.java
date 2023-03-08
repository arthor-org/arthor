package com.arthor.core.env.service;

import com.arthor.core.env.DefaultEnv;
import com.arthor.core.env.store.EnvDO;
import com.arthor.core.env.store.EnvStore;
import com.arthor.core.common.utils.Assert;

public class DefaultEnvService implements EnvService {

    private final EnvStore envStore;

    public DefaultEnvService(EnvStore envStore) {
        this.envStore = envStore;
    }

    @Override
    public com.arthor.core.env.Env findById(Long id) {
        EnvDO env = envStore.findById(id);
        Assert.notNull(env, "无法获取环境信息,id:[ " + id + " ]");
        return convert(env);
    }

    private com.arthor.core.env.Env convert(EnvDO entity) {
        com.arthor.core.env.Env env = new DefaultEnv();
        env.setId(entity.getId());
        env.setName(entity.getName());
        env.setDescription(entity.getDescription());
        env.setHost(entity.getHost());
        return env;
    }

}
