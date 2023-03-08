package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.CreateDeployCommandRequest;
import com.arthor.core.command.request.ScaleDeployCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.deploy.Deployment;

import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;

public abstract class AbstractScaleDeployCommand
        extends AbstractDeployCommand<ScaleDeployCommandRequest, Deployment> {

    protected Deployment targetDeployment;

    @Override
    public CommandResult<Deployment> execute(CommandContext commandContext, ScaleDeployCommandRequest input) {
        // 设置
        Deployment targetDeployment = commandContext.getDeploymentService().findById(input.getTargetId());
        setTargetDeployment(targetDeployment);

        // 检查
        if (check()) {
            return CommandResult.failure("该部署无法进行扩缩容操作");
        }

        // 构建扩缩容请求
        CreateDeployCommandRequest createDeployCommandRequest = buildScaleDeployCommandRequest(input);

        // 构建命令
        AbstractCreateDeployCommand createDeployCommand = commandContext.getCommandFactory().createDeployCommand();

        // 执行
        return commandContext.getCommandExecutor().execute(createDeployCommand, createDeployCommandRequest);
    }

    /**
     * 检查是否无法进行扩缩容
     *
     * @return
     */
    protected boolean check() {
        return DEPLOYED != targetDeployment.getStatus();
    }

    /**
     * 构建扩缩容请求
     *
     * @param input
     * @return
     */
    protected CreateDeployCommandRequest buildScaleDeployCommandRequest(ScaleDeployCommandRequest input) {
        CreateDeployCommandRequest createDeployCommandRequest = new CreateDeployCommandRequest(OperateEnum.SCALE);
        createDeployCommandRequest.setBuildRecordId(targetDeployment.getBuildRecordId());
        createDeployCommandRequest.setExternal(targetDeployment.getExternal());
        String symbol = ALL.resolveSymbol(targetDeployment.getApplicationName(), targetDeployment.getSymbol());
        createDeployCommandRequest.setSymbol(symbol);
        createDeployCommandRequest.setReplicas(input.getReplicas());
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
