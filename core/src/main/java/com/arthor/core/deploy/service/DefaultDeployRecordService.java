package com.arthor.core.deploy.service;

import com.alibaba.fastjson.JSON;
import com.arthor.core.deploy.model.CreateDeployRecordRequest;
import com.arthor.core.deploy.store.DeployRecordDO;
import com.arthor.core.deploy.store.DeployRecordStore;
import com.arthor.core.common.utils.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

public class DefaultDeployRecordService implements DeployRecordService {

    private final DeployRecordStore deployRecordStore;

    public DefaultDeployRecordService(DeployRecordStore deployRecordStore) {
        this.deployRecordStore = deployRecordStore;
    }

    @Override
    public void record(CreateDeployRecordRequest request) {
        DeployRecordDO entity = new DeployRecordDO();
        entity.setOperate(request.getOperate());
        entity.setTargetId(request.getTarget().getId());
        entity.setTargetSnapshot(JSON.toJSONString(request.getTarget()));
        if (Objects.nonNull(request.getRelated())) {
            entity.setRelatedId(request.getRelated().getId());
            entity.setRelatedSnapshot(JSON.toJSONString(request.getRelated()));
        }
        entity.setCreateTime(LocalDateTime.now());
        Assert.isTrue(deployRecordStore.create(entity), "保存部署记录失败");
    }

}
