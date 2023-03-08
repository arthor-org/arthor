package com.arthor.server.facade.impl;

import com.arthor.core.application.Application;
import com.arthor.core.application.service.ApplicationService;
import com.arthor.core.build.BuildRecord;
import com.arthor.server.model.dto.BuildRecordDTO;
import com.arthor.core.build.model.CreateBuildRequest;
import com.arthor.core.build.model.ListBuildRecordRequest;
import com.arthor.core.build.service.BuildRecordService;
import com.arthor.core.common.constant.ALL;
import com.arthor.core.common.enumeration.BuildRecordStatusEnum;
import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.env.Env;
import com.arthor.core.env.service.EnvService;
import com.arthor.core.feature.Feature;
import com.arthor.core.feature.service.FeatureService;
import com.arthor.core.integration.pipeline.jenkins.*;
import com.arthor.core.integration.pipeline.model.PipelineBuildInfoDTO;
import com.arthor.core.integration.pipeline.model.PipelineNextBuildIdRequest;
import com.arthor.core.integration.pipeline.service.PipelineOpenApiService;
import com.arthor.core.lock.service.LockService;
import com.arthor.core.pipeline.Pipeline;
import com.arthor.core.pipeline.model.ImageIdParseResult;
import com.arthor.core.pipeline.service.PipelineService;
import com.arthor.server.facade.BuildFacade;
import com.arthor.server.model.param.build.BuildParam;
import com.arthor.server.model.param.build.ListBuildRecordParam;
import com.arthor.server.utils.PipelineUtils;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.arthor.core.common.utils.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuildFacadeImpl implements BuildFacade {

    private final PipelineService pipelineService;
    private final PipelineOpenApiService pipelineOpenApiService;
    private final JenkinsOpenApiService jenkinsOpenApiService;
    private final LockService lockService;
    private final FeatureService featureService;
    private final ApplicationService applicationService;
    private final BuildRecordService buildRecordService;
    private final EnvService envService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean build(BuildParam param) {
        Feature feature = featureService.findById(param.getFeatureId());
        Long applicationId = feature.getApplicationId();
        Application application = applicationService.findById(applicationId);
        Pipeline pipeline = pipelineService.findById(application.getPipelineId());
        Assert.isTrue(PipelineStatusEnum.CREATED == pipeline.getStatus(), "该流水线尚未创建完成");
        Env env = envService.findById(param.getEnvId());

        // 应用共用同一流水线,故锁应用
        boolean tryLock = lockService.tryLock(lockEntry(env.getName(), application.getName()));
        Assert.isTrue(tryLock, "获取应用构建锁失败");

        PipelineNextBuildIdRequest pipelineNextBuildIdRequest = new JenkinsNextBuildIdRequest(pipeline.getJobName());
        String nextBuildId = pipelineOpenApiService.nextBuildId(pipelineNextBuildIdRequest);
        CreateBuildRequest createBuildRequest = new CreateBuildRequest();
        createBuildRequest.setApplicationId(applicationId);
        createBuildRequest.setApplicationName(application.getName());
        createBuildRequest.setFeatureId(feature.getId());
        createBuildRequest.setFeatureName(feature.getName());
        createBuildRequest.setBuildNumber(nextBuildId);
        createBuildRequest.setJobName(pipeline.getJobName());
        createBuildRequest.setEnvId(env.getId());
        createBuildRequest.setStatus(BuildRecordStatusEnum.BUILDING);
        createBuildRequest.setNumberOfCheck(0);
        createBuildRequest.setCreateTime(LocalDateTime.now());
        buildRecordService.createBuildRecord(createBuildRequest);
        JenkinsPipelineBuildRequest jenkinsPipelineBuildRequest = new JenkinsPipelineBuildRequest(application.getName(),
                feature.getRepositoryUrl(), feature.getBranch(), env.getName());
        jenkinsOpenApiService.buildWithParameters(jenkinsPipelineBuildRequest);

        boolean releaseLock = lockService.releaseLock(lockEntry(env.getName(), application.getName()));
        Assert.isTrue(releaseLock, "释放应用构建锁失败");

        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void checkBuildInfo() {
        ListBuildRecordRequest listBuildRecordRequest = new ListBuildRecordRequest();
        listBuildRecordRequest.setStatus(BuildRecordStatusEnum.BUILDING);
        List<BuildRecord> buildRecordList = buildRecordService.listByCondition(listBuildRecordRequest);
        List<BuildRecord> updateBuildRecordList = Lists.newArrayList();
        buildRecordList.forEach(buildRecord -> {
            JenkinsPipelineBuildInfoRequest jenkinsPipelineBuildInfoRequest = new JenkinsPipelineBuildInfoRequest(buildRecord.getJobName(), buildRecord.getBuildNumber());
            PipelineBuildInfoDTO pipelineBuildInfoDTO = jenkinsOpenApiService.buildInfo(jenkinsPipelineBuildInfoRequest);
            JenkinsPipelineBuildInfoDTO jenkinsPipelineBuildInfoDTO = null;
            boolean hit = false;
            if (Objects.isNull(pipelineBuildInfoDTO)) {
                // buildJenkinsPipelineAsync,这里不能协助构建,因为构建会自增buildNumber,到时候无法获取构建详情了
                if (shouldSkip(buildRecord.getNumberOfCheck())
                        && buildRecordService.increaseNumberOfCheck(buildRecord.getId())) {
                    // 增加检查次数
                    log.warn("Failed to get BuildInfo,building has not begun. pipeline:[{}], buildNumber:[{}]"
                            , buildRecord.getJobName(), buildRecord.getBuildNumber());
                } else {
                    hit = true;
                    buildRecord.setStatus(BuildRecordStatusEnum.FAILURE);
                }
            } else {
                jenkinsPipelineBuildInfoDTO = (JenkinsPipelineBuildInfoDTO) pipelineBuildInfoDTO;
                if (!jenkinsPipelineBuildInfoDTO.isBuilding()) {
                    hit = true;
                    buildRecord.setStatus(BuildRecordStatusEnum.valueOf(jenkinsPipelineBuildInfoDTO.getResult()));
                }
            }
            if (hit) {
                if (Objects.nonNull(jenkinsPipelineBuildInfoDTO) && StringUtils.isNotBlank(jenkinsPipelineBuildInfoDTO.getDescription())) {
                    ImageIdParseResult imageIdParseResult = PipelineUtils.parseImageId(jenkinsPipelineBuildInfoDTO.getDescription());
                    buildRecord.setCommitId(imageIdParseResult.getCommitId());
                    buildRecord.setImageId(jenkinsPipelineBuildInfoDTO.getDescription());
                }
                buildRecord.setFinishTime(LocalDateTime.now());
                updateBuildRecordList.add(buildRecord);
            }
        });
        buildRecordService.updateBuildRecordBatch(updateBuildRecordList);
    }

    @Override
    public List<BuildRecordDTO> listByCondition(ListBuildRecordParam param) {
        ListBuildRecordRequest listBuildRecordRequest = new ListBuildRecordRequest();
        listBuildRecordRequest.setEnvId(param.getEnvId());
        listBuildRecordRequest.setFeatureId(param.getFeatureId());
        listBuildRecordRequest.setStatus(param.getStatus());
        return buildRecordService.listByCondition(listBuildRecordRequest).stream()
                .map(BuildRecordDTO::valueOf).collect(Collectors.toList());
    }

    private final static String BUILD_LOCK_FORMAT = "build-lock-%s-%s";

    private String lockEntry(String env, String app) {
        return String.format(BUILD_LOCK_FORMAT, env, app);
    }

    private boolean shouldSkip(Integer numberOfCheck) {
        return numberOfCheck < ALL.MAX_NUMBER_OF_CHECK;
    }


}
