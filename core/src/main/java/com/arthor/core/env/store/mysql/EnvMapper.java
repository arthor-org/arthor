package com.arthor.core.env.store.mysql;

import com.arthor.core.env.store.EnvDO;

public interface EnvMapper {

    EnvDO findById(Long id);

}
