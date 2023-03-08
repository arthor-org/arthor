package com.arthor.core.deploy.store.mysql;

import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.deploy.store.DeploymentDO;

import java.util.List;

public interface DeploymentMapper {
    List<DeploymentDO> list(ListDeploymentRequest request);

    DeploymentDO findById(Long id);

    int save(DeploymentDO entity);

    int update(DeploymentDO entity);
}
