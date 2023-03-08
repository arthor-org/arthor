package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.ReplaceDeployCommandRequest;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

import static com.arthor.core.common.enumeration.DeployStatusEnum.*;

public abstract class AbstractReplaceDeployCommand
        extends AbstractDeployCommand<ReplaceDeployCommandRequest, Void> {

    protected Deployment deployment;
    protected List<Deployment> replacedDeploymentList;

    @Override
    public CommandResult<Void> execute(CommandContext commandContext, ReplaceDeployCommandRequest input) {
        // 部署
        Deployment deployment = input.getDeployment();
        setDeployment(deployment);

        // 被替换的部署列表
        List<Deployment> replacedDeploymentList = replacedDeploymentList(commandContext);
        setReplacedDeploymentList(replacedDeploymentList);

        boolean replacing = CollectionUtils.isNotEmpty(replacedDeploymentList);
        if (replacing) {
            for (Deployment replacedDeployment : replacedDeploymentList) {
                // 构建更新部署请求
                CreateDeploymentRequest updateDeploymentRequest = buildUpdateDeployRequest(replacedDeployment);
                // 更新
                commandContext.getDeploymentService().updateDeployment(updateDeploymentRequest);
                // 日志
                commandContext.getDeployRecordService().record(
                        buildCreateDeployRecordRequest(input.getOperate(), replacedDeployment, deployment));
            }
        }
        return CommandResult.success();
    }

    /**
     * 构建更新部署请求
     *
     * @param replacedDeployment
     * @return
     */
    protected abstract CreateDeploymentRequest buildUpdateDeployRequest(Deployment replacedDeployment);

    /**
     * 需要被替换的部署列表
     *
     * @param commandContext
     * @return
     */
    protected List<Deployment> replacedDeploymentList(CommandContext commandContext) {
        ListDeploymentRequest listDeploymentRequest = new ListDeploymentRequest();
        listDeploymentRequest.setApplicationId(deployment.getApplicationId());
        listDeploymentRequest.setStatusList(List.of(DEPLOYING, DEPLOYED, REPLACING));
        listDeploymentRequest.setTickThreshold(deployment.getTick());
        listDeploymentRequest.setSymbol(deployment.getSymbol());
        listDeploymentRequest.setCanary(deployment.getCanary());
        return commandContext.getDeploymentService().listByCondition(listDeploymentRequest);
    }

    public void setDeployment(Deployment deployment) {
        this.deployment = deployment;
    }

    public void setReplacedDeploymentList(List<Deployment> replacedDeploymentList) {
        this.replacedDeploymentList = replacedDeploymentList;
    }
}
