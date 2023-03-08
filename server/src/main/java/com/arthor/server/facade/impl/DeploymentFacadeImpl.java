package com.arthor.server.facade.impl;

import com.arthor.core.command.*;
import com.arthor.core.command.executor.CommandExecutor;
import com.arthor.core.command.factory.CommandFactory;
import com.arthor.core.command.request.*;
import com.arthor.core.common.enumeration.CanaryTypeEnum;
import com.arthor.core.common.enumeration.OperateEnum;
import com.arthor.core.common.utils.Assert;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.deploy.service.DeploymentService;
import com.arthor.core.spring.boot.stater.properties.KubernetesAutoProperties;
import com.arthor.server.facade.DeploymentFacade;
import com.arthor.server.model.dto.DeploymentDTO;
import com.arthor.server.model.param.IdParam;
import com.arthor.server.model.param.ListDeploymentParam;
import com.arthor.server.model.param.deploy.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeploymentFacadeImpl implements DeploymentFacade {

    private final CommandFactory commandFactory;
    private final CommandExecutor commandExecutor;
    private final KubernetesAutoProperties kubernetesAutoProperties;
    private final DeploymentService deploymentService;
    
    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean deploy(DeployParam param) {
        // 创建部署命令
        String symbol = StringUtils.isNotBlank(param.getSymbol()) ?
                param.getSymbol() : UUID.randomUUID().toString().replace("-", "");
        CreateDeployCommandRequest createDeployCommandRequest =
                CommandRequest.buildCreateDeployCommandRequest(param.getBuildRecordId(), param.getExternal(),
                        symbol, param.getReplicas(), kubernetesAutoProperties.getDefaultImagePullPolicy(),
                        kubernetesAutoProperties.getDefaultPodPort(), OperateEnum.DEPLOY);
        AbstractCreateDeployCommand createDeployCommand = commandFactory.createDeployCommand();
        CommandResult<Deployment> createDeployCommandResult = commandExecutor.execute(createDeployCommand, createDeployCommandRequest);
        Assert.isTrue(createDeployCommandResult.isSuccess(), createDeployCommandResult.getErrorMessage());

        // 替换部署命令
        ReplaceDeployCommandRequest replaceDeployCommandRequest =
                CommandRequest.buildReplaceDeployCommandRequest(createDeployCommandResult.getOutput());
        AbstractReplaceDeployCommand replaceDeployCommand = commandFactory.replaceDeployCommand();
        CommandResult<Void> replaceDeployCommandResult = commandExecutor.execute(replaceDeployCommand, replaceDeployCommandRequest);
        Assert.isTrue(replaceDeployCommandResult.isSuccess(), replaceDeployCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean gray(GrayParam param) {
        // 创建部署命令
        String symbol = StringUtils.isNotBlank(param.getSymbol()) ?
                param.getSymbol() : UUID.randomUUID().toString().replace("-", "");
        CreateDeployCommandRequest createDeployCommandRequest =
                CommandRequest.buildCreateDeployCommandRequest(param.getBuildRecordId(), param.getExternal(),
                        symbol, param.getReplicas(), kubernetesAutoProperties.getDefaultImagePullPolicy(),
                        kubernetesAutoProperties.getDefaultPodPort(), CanaryTypeEnum.HEADER,
                        param.getGrayHeaderValue(), OperateEnum.GRAY);
        AbstractCreateDeployCommand createDeployCommand = commandFactory.createDeployCommand();
        CommandResult<Deployment> createDeployCommandResult = commandExecutor.execute(createDeployCommand, createDeployCommandRequest);
        Assert.isTrue(createDeployCommandResult.isSuccess(), createDeployCommandResult.getErrorMessage());

        // 替换部署命令
        ReplaceDeployCommandRequest replaceDeployCommandRequest =
                CommandRequest.buildReplaceDeployCommandRequest(createDeployCommandResult.getOutput());
        AbstractReplaceDeployCommand replaceDeployCommand = commandFactory.replaceDeployCommand();
        CommandResult<Void> replaceDeployCommandResult = commandExecutor.execute(replaceDeployCommand, replaceDeployCommandRequest);
        Assert.isTrue(replaceDeployCommandResult.isSuccess(), replaceDeployCommandResult.getErrorMessage());
        
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean blueGreen(BlueGreenParam param) {
        // 创建部署命令
        String symbol = StringUtils.isNotBlank(param.getSymbol()) ?
                param.getSymbol() : UUID.randomUUID().toString().replace("-", "");
        CreateDeployCommandRequest createDeployCommandRequest =
                CommandRequest.buildCreateDeployCommandRequest(param.getBuildRecordId(), param.getExternal(),
                        symbol, param.getReplicas(), kubernetesAutoProperties.getDefaultImagePullPolicy(),
                        kubernetesAutoProperties.getDefaultPodPort(), CanaryTypeEnum.WEIGHT, param.getWeight(), OperateEnum.BLUE_GREEN);
        AbstractCreateDeployCommand createDeployCommand = commandFactory.createDeployCommand();
        CommandResult<Deployment> createDeployCommandResult = commandExecutor.execute(createDeployCommand, createDeployCommandRequest);
        Assert.isTrue(createDeployCommandResult.isSuccess(), createDeployCommandResult.getErrorMessage());

        // 替换部署命令
        ReplaceDeployCommandRequest replaceDeployCommandRequest =
                CommandRequest.buildReplaceDeployCommandRequest(createDeployCommandResult.getOutput());
        AbstractReplaceDeployCommand replaceDeployCommand = commandFactory.replaceDeployCommand();
        CommandResult<Void> replaceDeployCommandResult = commandExecutor.execute(replaceDeployCommand, replaceDeployCommandRequest);
        Assert.isTrue(replaceDeployCommandResult.isSuccess(), replaceDeployCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean updateBlueGreen(UpdateBlueGreenParam param) {
        UpdateBlueGreenDeployCommandRequest updateBlueGreenDeployCommandRequest = new UpdateBlueGreenDeployCommandRequest();
        updateBlueGreenDeployCommandRequest.setGreenDeploymentId(param.getGreenDeploymentId());
        updateBlueGreenDeployCommandRequest.setWeight(param.getWeight());
        AbstractUpdateBlueGreenDeployCommand updateBlueGreenDeployCommand = commandFactory.updateBlueGreenDeployCommand();
        CommandResult<Deployment> updateBlueGreenCommandResult = commandExecutor.execute(updateBlueGreenDeployCommand, updateBlueGreenDeployCommandRequest);
        Assert.isTrue(updateBlueGreenCommandResult.isSuccess(), updateBlueGreenCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean delete(IdParam param) {
        DeleteDeployCommandRequest deleteDeployCommandRequest = new DeleteDeployCommandRequest();
        deleteDeployCommandRequest.setTargetId(param.getId());
        AbstractDeleteDeployCommand deleteDeployCommand = commandFactory.deleteDeployCommand();
        CommandResult<Void> deleteCommandResult = commandExecutor.execute(deleteDeployCommand, deleteDeployCommandRequest);
        Assert.isTrue(deleteCommandResult.isSuccess(), deleteCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean rollback(RollbackParam param) {
        // 创建部署命令
        RollbackDeployCommandRequest rollbackDeployCommandRequest = new RollbackDeployCommandRequest();
        rollbackDeployCommandRequest.setTargetId(param.getTargetId());
        AbstractRollbackDeployCommand rollbackDeployCommand = commandFactory.createRollbackDeployCommand();
        CommandResult<Deployment> rollbackDeployCommandResult = commandExecutor.execute(rollbackDeployCommand, rollbackDeployCommandRequest);
        Assert.isTrue(rollbackDeployCommandResult.isSuccess(), rollbackDeployCommandResult.getErrorMessage());

        // 替换
        ReplaceDeployCommandRequest replaceDeployCommandRequest = new ReplaceDeployCommandRequest();
        replaceDeployCommandRequest.setDeployment(rollbackDeployCommandResult.getOutput());
        AbstractReplaceDeployCommand replaceDeployCommand = commandFactory.replaceDeployCommand();
        CommandResult<Void> replaceDeployCommandResult = commandExecutor.execute(replaceDeployCommand, replaceDeployCommandRequest);
        Assert.isTrue(replaceDeployCommandResult.isSuccess(), replaceDeployCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean scale(ScaleParam param) {
        // 由于无法控制扩缩容的节点,故采用替换部署的方式
        // 创建部署
        ScaleDeployCommandRequest scaleDeployCommandRequest = new ScaleDeployCommandRequest();
        scaleDeployCommandRequest.setTargetId(param.getTargetId());
        scaleDeployCommandRequest.setReplicas(param.getReplicas());
        AbstractScaleDeployCommand scaleDeployCommand = commandFactory.createScaleDeployCommand();
        CommandResult<Deployment> scaleDeployCommandResult = commandExecutor.execute(scaleDeployCommand, scaleDeployCommandRequest);
        Assert.isTrue(scaleDeployCommandResult.isSuccess(), scaleDeployCommandResult.getErrorMessage());

        // 清理
        DeleteDeployCommandRequest deleteDeployCommandRequest = new DeleteDeployCommandRequest();
        deleteDeployCommandRequest.setTargetId(param.getTargetId());
        deleteDeployCommandRequest.setRelatedId(scaleDeployCommandResult.getOutput().getId());
        AbstractDeleteDeployCommand deleteDeployCommand = commandFactory.deleteDeployCommand();
        CommandResult<Void> deleteDeployCommandResult = commandExecutor.execute(deleteDeployCommand, deleteDeployCommandRequest);
        Assert.isTrue(deleteDeployCommandResult.isSuccess(), deleteDeployCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean promote(IdParam param) {
        // 创建部署
        PromoteDeployCommandRequest promoteDeployCommandRequest = new PromoteDeployCommandRequest();
        promoteDeployCommandRequest.setTargetId(param.getId());
        AbstractPromoteDeployCommand promoteDeployCommand = commandFactory.createPromoteDeployCommand();
        CommandResult<Deployment> promoteDeployCommandResult = commandExecutor.execute(promoteDeployCommand, promoteDeployCommandRequest);
        Assert.isTrue(promoteDeployCommandResult.isSuccess(), promoteDeployCommandResult.getErrorMessage());

        Deployment deployment = promoteDeployCommandResult.getOutput();
        // 替换的部署
        ReplaceDeployCommandRequest replaceDeployCommandRequest = new ReplaceDeployCommandRequest();
        replaceDeployCommandRequest.setDeployment(deployment);
        AbstractReplaceDeployCommand replaceDeployCommand = commandFactory.replaceDeployCommand();
        CommandResult<Void> replaceDeployCommandResult = commandExecutor.execute(replaceDeployCommand, replaceDeployCommandRequest);
        Assert.isTrue(replaceDeployCommandResult.isSuccess(), replaceDeployCommandResult.getErrorMessage());

        // 清理
        DeleteDeployCommandRequest deleteDeployCommandRequest = new DeleteDeployCommandRequest();
        deleteDeployCommandRequest.setTargetId(param.getId());
        deleteDeployCommandRequest.setRelatedId(deployment.getId());
        AbstractDeleteDeployCommand deleteDeployCommand = commandFactory.deleteDeployCommand();
        CommandResult<Void> deleteDeployCommandResult = commandExecutor.execute(deleteDeployCommand, deleteDeployCommandRequest);
        Assert.isTrue(deleteDeployCommandResult.isSuccess(), deleteDeployCommandResult.getErrorMessage());

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void check() {
        AbstractDispatchExecuteCommand dispatchExecuteCommand = commandFactory.dispatchExecuteCommand();
        commandExecutor.execute(dispatchExecuteCommand, null);
    }

    @Override
    public List<DeploymentDTO> listByCondition(ListDeploymentParam param) {
        ListDeploymentRequest listDeploymentRequest = new ListDeploymentRequest();
        listDeploymentRequest.setApplicationId(param.getApplicationId());
        listDeploymentRequest.setSymbol(param.getSymbol());
        listDeploymentRequest.setBuildId(param.getBuildId());
        listDeploymentRequest.setTickThreshold(param.getTickThreshold());
        listDeploymentRequest.setCanary(param.getCanary());
        listDeploymentRequest.setStatusList(param.getStatusList());
        return deploymentService.listByCondition(listDeploymentRequest).stream()
                .map(DeploymentDTO::valueOf).collect(Collectors.toList());
    }

}
