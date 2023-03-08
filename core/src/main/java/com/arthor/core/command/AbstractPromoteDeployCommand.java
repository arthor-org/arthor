package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.CreateDeployCommandRequest;
import com.arthor.core.command.request.PromoteDeployCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

import static com.arthor.core.common.constant.ALL.STABLE_SYMBOL;

public abstract class AbstractPromoteDeployCommand
        extends AbstractDeployCommand<PromoteDeployCommandRequest, Deployment> {

    protected Deployment targetDeployment;

    @Override
    public CommandResult<Deployment> execute(CommandContext commandContext, PromoteDeployCommandRequest input) {
        // 设置
        Deployment targetDeployment = commandContext.getDeploymentService().findById(input.getTargetId());
        setTargetDeployment(targetDeployment);

        // 检查
        if (check()) {
            return CommandResult.failure("该部署已被提升,请勿重复操作");
        }

        // 构建提升部署请求
        CreateDeployCommandRequest createDeployCommandRequest = buildPromoteDeployCommandRequest();

        // 构建命令
        AbstractCreateDeployCommand createDeployCommand = commandContext.getCommandFactory().createDeployCommand();

        // 执行
        return commandContext.getCommandExecutor().execute(createDeployCommand, createDeployCommandRequest);
    }

    /**
     * 检查是否无法进行提升
     *
     * @return
     */
    protected boolean check() {
        return ALL.isStable(targetDeployment.getSymbol());
    }

    /**
     * 构建提升部署请求
     *
     * @return
     */
    protected CreateDeployCommandRequest buildPromoteDeployCommandRequest() {
        CreateDeployCommandRequest createDeployCommandRequest = new CreateDeployCommandRequest(OperateEnum.PROMOTE);
        createDeployCommandRequest.setBuildRecordId(targetDeployment.getBuildRecordId());
        createDeployCommandRequest.setExternal(targetDeployment.getExternal());
        createDeployCommandRequest.setSymbol(STABLE_SYMBOL);
        createDeployCommandRequest.setReplicas(targetDeployment.getDeploymentReplicas());
        createDeployCommandRequest.setImagePullPolicy(targetDeployment.getDeploymentImagePullPolicy());
        createDeployCommandRequest.setPodPort(targetDeployment.getDeploymentContainerPort());
        createDeployCommandRequest.setCanary(Boolean.FALSE);
        return createDeployCommandRequest;
    }

    public void setTargetDeployment(Deployment targetDeployment) {
        this.targetDeployment = targetDeployment;
    }

}
