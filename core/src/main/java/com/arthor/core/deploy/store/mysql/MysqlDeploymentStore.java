package com.arthor.core.deploy.store.mysql;

import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.deploy.store.DeploymentDO;
import com.arthor.core.deploy.store.DeploymentStore;
import com.arthor.core.deploy.store.mysql.DeploymentMapper;

import java.util.List;

public class MysqlDeploymentStore implements DeploymentStore {

    private final DeploymentMapper deploymentMapper;

    public MysqlDeploymentStore(DeploymentMapper deploymentMapper) {
        this.deploymentMapper = deploymentMapper;
    }

    @Override
    public DeploymentDO findById(Long id) {
        return deploymentMapper.findById(id);
    }

    @Override
    public List<DeploymentDO> list(ListDeploymentRequest request) {
        return deploymentMapper.list(request);
    }

    @Override
    public Boolean save(DeploymentDO entity) {
        return SqlHelper.retBool(deploymentMapper.save(entity));
    }

    @Override
    public Boolean update(DeploymentDO entity) {
        return SqlHelper.retBool(deploymentMapper.update(entity));
    }
}
