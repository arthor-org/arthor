package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.UpdateBlueGreenDeployCommandRequest;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;

import static com.arthor.core.common.enumeration.DeployItemStatusEnum.PROCESSING;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYING;
import static com.arthor.core.common.enumeration.LifecycleEnum.MODIFYING;

public abstract class AbstractUpdateBlueGreenDeployCommand
        extends AbstractDeployCommand<UpdateBlueGreenDeployCommandRequest, Deployment> {

    protected Deployment greenDeployment;

    @Override
    public CommandResult<Deployment> execute(CommandContext commandContext, UpdateBlueGreenDeployCommandRequest input) {
        // 设置
        Deployment greenDeployment = commandContext.getDeploymentService().findById(input.getGreenDeploymentId());
        setGreenDeployment(greenDeployment);

        // 检查
        if (check()) {
            return CommandResult.failure("该部署无法进行蓝绿比例调整");
        }

        // 构建更新蓝绿请求
        CreateDeploymentRequest updateBlueGreenRequest = buildUpdateBlueGreenDeployCommandRequest(commandContext, input);

        // 更新
        commandContext.getDeploymentService().updateDeployment(updateBlueGreenRequest);

        // 日志
        commandContext.getDeployRecordService().record(
                buildCreateDeployRecordRequest(input.getOperate(), greenDeployment));

        return CommandResult.success();
    }

    /**
     * 构建更新蓝绿请求
     *
     * @return
     */
    protected CreateDeploymentRequest buildUpdateBlueGreenDeployCommandRequest(CommandContext commandContext,
                                                                               UpdateBlueGreenDeployCommandRequest input) {
        CreateDeploymentRequest updateBlueGreenRequest = new CreateDeploymentRequest();
        updateBlueGreenRequest.setId(greenDeployment.getId());
        updateBlueGreenRequest.setTick(commandContext.getCounterService().getAndIncrement());
        updateBlueGreenRequest.setCanaryValue(input.getWeight());
        updateBlueGreenRequest.setStatus(DEPLOYING);
        // 变更元数据
        updateBlueGreenRequest.setLifecycle(MODIFYING);
        // 变更路由
        updateBlueGreenRequest.setRouteStatus(PROCESSING);
        commandContext.getDeploymentService().updateDeployment(updateBlueGreenRequest);
        return updateBlueGreenRequest;
    }

    /**
     * 检查是否无法进行蓝绿比例调整
     *
     * @return
     */
    protected boolean check() {
        return DEPLOYED != greenDeployment.getStatus()
                || !greenDeployment.getCanary()
                || CanaryTypeEnum.WEIGHT != greenDeployment.getCanaryType();
    }

    public void setGreenDeployment(Deployment greenDeployment) {
        this.greenDeployment = greenDeployment;
    }

}
