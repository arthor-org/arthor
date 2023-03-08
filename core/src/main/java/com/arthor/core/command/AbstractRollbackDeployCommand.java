package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.CreateDeployCommandRequest;
import com.arthor.core.command.request.RollbackDeployCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

public abstract class AbstractRollbackDeployCommand
        extends AbstractDeployCommand<RollbackDeployCommandRequest, Deployment> {

    protected Deployment targetDeployment;

    @Override
    public CommandResult<Deployment> execute(CommandContext commandContext, RollbackDeployCommandRequest input) {
        // 设置
        Deployment targetDeployment = commandContext.getDeploymentService().findById(input.getTargetId());
        setTargetDeployment(targetDeployment);

        // 构建回滚部署请求
        CreateDeployCommandRequest createDeployCommandRequest = buildRollbackDeployCommandRequest();

        // 构建命令
        Command<CreateDeployCommandRequest, Deployment> createDeployCommand = commandContext.getCommandFactory().createDeployCommand();

        // 执行
        return commandContext.getCommandExecutor().execute(createDeployCommand, createDeployCommandRequest);
    }

    /**
     * 构建回滚部署请求
     *
     * @return
     */
    protected CreateDeployCommandRequest buildRollbackDeployCommandRequest() {
        CreateDeployCommandRequest createDeployCommandRequest = new CreateDeployCommandRequest(OperateEnum.ROLLBACK);
        createDeployCommandRequest.setBuildRecordId(targetDeployment.getBuildRecordId());
        createDeployCommandRequest.setExternal(targetDeployment.getExternal());
        String symbol = ALL.resolveSymbol(targetDeployment.getApplicationName(), targetDeployment.getSymbol());
        createDeployCommandRequest.setSymbol(symbol);
        createDeployCommandRequest.setReplicas(targetDeployment.getDeploymentReplicas());
        createDeployCommandRequest.setImagePullPolicy(targetDeployment.getDeploymentImagePullPolicy());
        createDeployCommandRequest.setPodPort(targetDeployment.getDeploymentContainerPort());
        createDeployCommandRequest.setCanary(targetDeployment.getCanary());
        createDeployCommandRequest.setCanaryType(targetDeployment.getCanaryType());
        createDeployCommandRequest.setCanaryValue(targetDeployment.getCanaryValue());
        return createDeployCommandRequest;
    }

    public void setTargetDeployment(Deployment targetDeployment) {
        this.targetDeployment = targetDeployment;
    }
}
