package com.arthor.core.command;

import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.DeleteExecuteCommandRequest;
import com.arthor.core.command.request.DeployExecuteCommandRequest;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.integration.kubernetes.service.DefaultKubernetesOpenApiService;
import com.google.common.collect.Maps;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public abstract class AbstractDispatchExecuteCommand
        extends AbstractExecuteCommand<Void, Void> {

    private final static Logger log = LoggerFactory.getLogger(AbstractDispatchExecuteCommand.class);

    @Override
    public CommandResult<Void> execute(CommandContext commandContext, Void input) {
        // 候选部署列表
        List<Deployment> deploymentList = commandContext.getDeploymentService()
                .listByCondition(buildListDeploymentRequest());

        // 部署分组
        Map</*appId*/Long, Map</*env*/String, Map</*symbol*/String, List<Deployment>>>> deploymentGroup
                = groupDeployments(deploymentList);

        // 分发
        dispatch(commandContext, deploymentGroup);

        return CommandResult.success();
    }

    /**
     * 构建部署列表请求
     */
    protected abstract ListDeploymentRequest buildListDeploymentRequest();

    /**
     * 部署分组
     *
     * @param deploymentList
     * @return
     */
    protected Map</*appId*/Long, Map</*env*/String, Map</*symbol*/String, List<Deployment>>>> groupDeployments(List<Deployment> deploymentList) {
        Map<Long, Map<String, Map<String, List<Deployment>>>> deploymentGroup = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(deploymentList)) {
            deploymentList.forEach(d -> {
                Map<String, Map<String, List<Deployment>>> applicationGroup =
                        deploymentGroup.computeIfAbsent(d.getApplicationId(), key -> Maps.newHashMap());
                Map<String, List<Deployment>> envGroup =
                        applicationGroup.computeIfAbsent(d.getHost(), key -> Maps.newHashMap());
                List<Deployment> deployments = envGroup.computeIfAbsent(d.getSymbol(), key -> Lists.newArrayList());
                deployments.add(d);
            });
        }
        return deploymentGroup;
    }

    /**
     * 分发
     *
     * @param commandContext
     * @param deploymentGroup
     */
    protected void dispatch(CommandContext commandContext,
                            Map</*appId*/Long, Map</*env*/String, Map</*symbol*/String, List<Deployment>>>> deploymentGroup) {
        dispatchByApp(commandContext, deploymentGroup);
    }

    /**
     * 基于应用分发
     *
     * @param commandContext
     * @param deploymentGroup
     */
    protected void dispatchByApp(CommandContext commandContext, Map</*appId*/Long, Map</*env*/String, Map</*symbol*/String, List<Deployment>>>> deploymentGroup) {
        for (Map.Entry<Long, Map<String, Map<String, List<Deployment>>>> groupEntry : deploymentGroup.entrySet()) {
            dispatchByEnv(commandContext, groupEntry.getValue());
        }
    }

    /**
     * 基于环境分发
     *
     * @param commandContext
     * @param appGroup
     */
    protected void dispatchByEnv(CommandContext commandContext, Map<String, Map<String, List<Deployment>>> appGroup) {
        for (Map.Entry<String, Map<String, List<Deployment>>> envGroup : appGroup.entrySet()) {
            dispatchBySymbol(commandContext, envGroup.getValue());
        }
    }

    /**
     * 基于部署限定符分发
     *
     * @param commandContext
     * @param symbolGroup
     */
    protected void dispatchBySymbol(CommandContext commandContext, Map<String, List<Deployment>> symbolGroup) {
        for (Map.Entry<String, List<Deployment>> symbolEntry : symbolGroup.entrySet()) {
            dispatch(commandContext, symbolEntry.getValue());
        }
    }

    /**
     * 分发
     *
     * @param commandContext
     * @param deployments 同app,同环境,同symbol下,部署列表
     */
    protected void dispatch(CommandContext commandContext, List<Deployment> deployments) {
        // 基于tick排序
        deployments.sort((o1, o2) -> o2.getTick().compareTo(o1.getTick()));
        // 最终状态
        Deployment finalStateDeployment = deployments.remove(0);
        // 分发
        doDispatch(commandContext, finalStateDeployment);
        // 瞬态部署清理
        deployments.forEach(transientStateDeployment -> {
            DeleteExecuteCommandRequest deletingExecuteCommandRequest = new DeleteExecuteCommandRequest();
            deletingExecuteCommandRequest.setDeployment(transientStateDeployment);
            AbstractDeleteExecuteCommand deleteExecuteCommand = commandContext.getCommandFactory().deleteExecuteCommand();
            commandContext.getCommandExecutor().execute(deleteExecuteCommand, deletingExecuteCommandRequest);
        });
    }

    /**
     * 命令分发
     *
     * @param commandContext
     * @param deployment
     */
    protected void doDispatch(CommandContext commandContext, Deployment deployment) {
        switch (deployment.getStatus()) {
            case DEPLOYING:
                DeployExecuteCommandRequest deployingExecuteCommandRequest = new DeployExecuteCommandRequest();
                deployingExecuteCommandRequest.setDeployment(deployment);
                AbstractDeployExecuteCommand deployExecuteCommand = commandContext.getCommandFactory().deployExecuteCommand();
                commandContext.getCommandExecutor().execute(deployExecuteCommand, deployingExecuteCommandRequest);
                break;
            case REPLACING:
            case DELETING:
                DeleteExecuteCommandRequest deletingExecuteCommandRequest = new DeleteExecuteCommandRequest();
                deletingExecuteCommandRequest.setDeployment(deployment);
                AbstractDeleteExecuteCommand deleteExecuteCommand = commandContext.getCommandFactory().deleteExecuteCommand();
                commandContext.getCommandExecutor().execute(deleteExecuteCommand, deletingExecuteCommandRequest);
                break;
            default:
                log.error("Unknown deployment status:[{}]", deployment.getStatus());
        }
    }

}
