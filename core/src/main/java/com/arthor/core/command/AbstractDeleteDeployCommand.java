package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.DeleteDeployCommandRequest;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYING;

public abstract class AbstractDeleteDeployCommand
        extends AbstractDeployCommand<DeleteDeployCommandRequest, Void> {

    protected Long tick;

    protected Deployment deployment;

    @Override
    public CommandResult<Void> execute(CommandContext commandContext, DeleteDeployCommandRequest input) {
        // 设置
        Deployment deployment = commandContext.getDeploymentService().findById(input.getTargetId());
        setDeployment(deployment);

        // 检查
        if (check()) {
            return CommandResult.failure("该部署已下线");
        }

        // 如下线的部署为非canary,并且其存在canary部署,则不允许下线
        if (!canaryCheck(commandContext)) {
            return CommandResult.failure("该部署存在生效的Canary部署,下线失败");
        }

        // 对于非手动触发的删除命令,透传tick即可
        Long tick = Objects.nonNull(input.getRelatedId()) ?
                deployment.getTick() : commandContext.getCounterService().getAndIncrement();
        setTick(tick);

        // 构建删除部署请求
        CreateDeploymentRequest deleteDeploymentRequest = buildDeleteDeploymentRequest(commandContext, input);

        // 删除
        commandContext.getDeploymentService().updateDeployment(deleteDeploymentRequest);

        // 日志
        commandContext.getDeployRecordService().record(
                buildCreateDeployRecordRequest(input.getOperate(), deployment));

        return CommandResult.success();
    }

    /**
     * 构建删除部署请求
     *
     * @return
     */
    protected abstract CreateDeploymentRequest buildDeleteDeploymentRequest(CommandContext commandContext,
                                                                            DeleteDeployCommandRequest input);

    /**
     * 检查是否无法进行下线操作
     *
     * @return
     */
    protected boolean check() {
        return DEPLOYED != deployment.getStatus();
    }

    /**
     * 如下线的部署为非canary,并且其存在canary部署,则不允许下线
     *
     * @param commandContext
     * @return
     */
    protected boolean canaryCheck(CommandContext commandContext) {
        if (!deployment.getCanary()) {
            ListDeploymentRequest listDeploymentRequest = new ListDeploymentRequest();
            listDeploymentRequest.setApplicationId(deployment.getApplicationId());
            listDeploymentRequest.setStatusList(List.of(DEPLOYING, DEPLOYED));
            listDeploymentRequest.setTickThreshold(deployment.getTick());
            // 查询灰度部署
            listDeploymentRequest.setSymbol(deployment.getSymbol() + ".canary");
            listDeploymentRequest.setCanary(Boolean.TRUE);
            List<Deployment> existingDeploymentList = commandContext.getDeploymentService().listByCondition(listDeploymentRequest);
            return CollectionUtils.isEmpty(existingDeploymentList);
        }
        return Boolean.TRUE;
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }
}
