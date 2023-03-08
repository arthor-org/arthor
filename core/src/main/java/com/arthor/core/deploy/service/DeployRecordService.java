package com.arthor.core.deploy.service;

import com.arthor.core.deploy.model.CreateDeployRecordRequest;

public interface DeployRecordService {

    /**
     * 记录部署操作记录
     *
     * @param request
     */
    void record(CreateDeployRecordRequest request);

}

