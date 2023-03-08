package com.arthor.core.application.service;

import com.arthor.core.application.Application;
import com.arthor.core.application.model.CreateApplicationRequest;
import com.arthor.core.application.model.ListApplicationRequest;

import java.util.List;

public interface ApplicationService {

    /**
     * 报错应用
     *
     * @param request
     * @return
     */
    void create(CreateApplicationRequest request);
    /**
     * 应用列表
     *
     * @param request
     * @return
     */
    List<Application> list(ListApplicationRequest request);
    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Application findById(Long id);

}
