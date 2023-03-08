package com.arthor.core.deploy.store;

import com.arthor.core.deploy.model.ListDeploymentRequest;

import java.util.List;

public interface DeploymentStore {
    DeploymentDO findById(Long id);
    List<DeploymentDO> list(ListDeploymentRequest request);

    Boolean save(DeploymentDO entity);

    Boolean update(DeploymentDO entity);
}
