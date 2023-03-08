package com.arthor.core.command;

import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeployRecordRequest;

/**
 * 部署
 */
public abstract class AbstractDeployCommand<I, O> implements Command<I, O> {

    protected CreateDeployRecordRequest buildCreateDeployRecordRequest(OperateEnum operate, Deployment target) {
        return buildCreateDeployRecordRequest(operate, target, null);
    }
    protected CreateDeployRecordRequest buildCreateDeployRecordRequest(OperateEnum operate,
                                                                       Deployment target, Deployment related) {
        CreateDeployRecordRequest createDeployRecordRequest = new CreateDeployRecordRequest();
        createDeployRecordRequest.setOperate(operate);
        createDeployRecordRequest.setTarget(target);
        createDeployRecordRequest.setRelated(related);
        return createDeployRecordRequest;
    }
}
