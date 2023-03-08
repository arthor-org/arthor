package com.arthor.core.env.service;

import com.arthor.core.env.Env;

public interface EnvService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Env findById(Long id);

}
