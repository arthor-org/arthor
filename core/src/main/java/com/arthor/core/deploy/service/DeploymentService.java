package com.arthor.core.deploy.service;

import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.model.FindDeploymentRequest;
import com.arthor.core.deploy.model.ListDeploymentRequest;

import java.util.List;

public interface DeploymentService {

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Deployment findById(Long id);

    /**
     * 列表
     *
     * @param request
     * @return
     */
    List<Deployment> listByCondition(ListDeploymentRequest request);

    /**
     * 更新
     *
     * @param request
     */
    void updateDeployment(CreateDeploymentRequest request);

    /**
     * 新增
     *
     * @param request
     */
    Deployment createDeployment(CreateDeploymentRequest request);

}
