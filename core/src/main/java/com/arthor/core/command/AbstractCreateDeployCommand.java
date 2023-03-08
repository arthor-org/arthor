package com.arthor.core.command;

import com.arthor.core.build.BuildRecord;
import com.arthor.core.command.context.CommandContext;
import com.arthor.core.command.request.CreateDeployCommandRequest;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.BuildRecordStatusEnum;
import com.arthor.core.deploy.Deployment;
import com.arthor.core.deploy.model.CreateDeploymentRequest;
import com.arthor.core.deploy.model.ListDeploymentRequest;
import com.arthor.core.env.Env;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYED;
import static com.arthor.core.common.enumeration.DeployStatusEnum.DEPLOYING;

public abstract class AbstractCreateDeployCommand
        extends AbstractDeployCommand<CreateDeployCommandRequest, Deployment> {

    protected BuildRecord buildRecord;
    protected Long tick;
    protected Env env;
    protected String deploymentName;
    protected String symbol;
    protected String deploySymbol;

    @Override
    public CommandResult<Deployment> execute(CommandContext commandContext, CreateDeployCommandRequest input) {
        // 检查构建记录
        BuildRecord buildRecord = commandContext
                .getBuildRecordService().findById(input.getBuildRecordId());
        if (BuildRecordStatusEnum.SUCCESS != buildRecord.getStatus()) {
            return CommandResult.failure("构建记录还未完成构建");
        }
        setBuildRecord(buildRecord);

        // 检查环境
        Env env = commandContext.getEnvService().findById(buildRecord.getEnvId());
        setEnv(env);

        // 部署名称,这里不会与部署限定符挂钩,部署限定符可能出现多部署共用的情况
        String deploymentName = buildRecord.getApplicationName() + "-" + UUID.randomUUID().toString().replace("-", "");
        setDeploymentName(deploymentName);

        // 入参的Symbol
        setSymbol(input.getSymbol());

        // 部署限定符
        String deploySymbol = input.getCanary() ?
                ALL.buildSymbol(buildRecord.getApplicationName(), input.getSymbol() + ".canary")
                : ALL.buildSymbol(buildRecord.getApplicationName(), input.getSymbol());
        setDeploySymbol(deploySymbol);

        // 逻辑时钟
        Long tick = commandContext.getCounterService().getAndIncrement();
        setTick(tick);

        // 由于canary依靠正常的部署,故这里会判断没有正常的情况下,不让这么部署
        if (!canaryCheck(commandContext, input)) {
            return CommandResult.failure("该部署暂不支持灰度部署,请先进行正常部署");
        }

        // 构建部署请求
        CreateDeploymentRequest createDeploymentRequest = buildCreateDeploymentRequest(commandContext, input);

        // 部署
        Deployment deployment = commandContext.getDeploymentService().createDeployment(createDeploymentRequest);

        // 日志
        if (Objects.nonNull(input.getOperate())) {
            commandContext.getDeployRecordService().record(
                    buildCreateDeployRecordRequest(input.getOperate(), deployment));
        }

        return CommandResult.success(deployment);
    }

    /**
     * 由于canary依靠正常的部署,故这里会判断没有正常的情况下,不让这么部署
     *
     * @param commandContext
     * @param input
     * @return
     */
    protected boolean canaryCheck(CommandContext commandContext, CreateDeployCommandRequest input) {
        if (input.getCanary()) {
            ListDeploymentRequest listDeploymentRequest = new ListDeploymentRequest();
            listDeploymentRequest.setApplicationId(buildRecord.getApplicationId());
            listDeploymentRequest.setStatusList(List.of(DEPLOYING, DEPLOYED));
            listDeploymentRequest.setTickThreshold(tick);
            // 查询正常部署,那么symbol就不能传入.canary
            listDeploymentRequest.setSymbol(ALL.buildSymbol(buildRecord.getApplicationName(), input.getSymbol()));
            listDeploymentRequest.setCanary(Boolean.FALSE);
            List<Deployment> normalDeployment = commandContext.getDeploymentService().listByCondition(listDeploymentRequest);
            return CollectionUtils.isNotEmpty(normalDeployment);
        }
        return Boolean.TRUE;
    }

    /**
     * 构建部署请求
     *
     * @param commandContext
     * @param input
     * @return
     */
    protected abstract CreateDeploymentRequest buildCreateDeploymentRequest(CommandContext commandContext,
                                                                            CreateDeployCommandRequest input);

    public void setBuildRecord(BuildRecord buildRecord) {
        this.buildRecord = buildRecord;
    }

    public void setTick(Long tick) {
        this.tick = tick;
    }

    public void setEnv(Env env) {
        this.env = env;
    }

    public void setDeploymentName(String deploymentName) {
        this.deploymentName = deploymentName;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setDeploySymbol(String deploySymbol) {
        this.deploySymbol = deploySymbol;
    }
}
