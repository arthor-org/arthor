package com.arthor.core.integration.pipeline.jenkins;


import com.arthor.core.integration.pipeline.exception.PipelineOpenApiException;
import com.arthor.core.integration.pipeline.model.*;
import com.arthor.core.integration.pipeline.service.PipelineOpenApiService;
import com.cdancy.jenkins.rest.JenkinsClient;
import com.cdancy.jenkins.rest.domain.common.Error;
import com.cdancy.jenkins.rest.domain.common.IntegerResponse;
import com.cdancy.jenkins.rest.domain.common.RequestStatus;
import com.cdancy.jenkins.rest.domain.job.BuildInfo;
import com.cdancy.jenkins.rest.domain.job.JobInfo;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.arthor.core.common.utils.Assert;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class JenkinsOpenApiService implements PipelineOpenApiService {

    private final static Logger log = LoggerFactory.getLogger(JenkinsOpenApiService.class);
    private final JenkinsClient jenkinsClient;
    private final JenkinsProperties jenkinsProperties;
    public JenkinsOpenApiService(JenkinsClient jenkinsClient,
                                 JenkinsProperties jenkinsProperties) {
        this.jenkinsClient = jenkinsClient;
        this.jenkinsProperties = jenkinsProperties;
    }

    private final ConfigBuilder configBuilder = new ConfigBuilder();

    @Override
    public void buildWithParameters(PipelineBuildRequest pipelineBuildRequest) {
        JenkinsPipelineBuildRequest request = (JenkinsPipelineBuildRequest) pipelineBuildRequest;
        Map<String, List<String>> properties = Maps.newHashMap();
        properties.put("repository_url", Collections.singletonList(request.getRepositoryUrl()));
        properties.put("branch", Collections.singletonList(request.getBranch()));
        properties.put("environment", Collections.singletonList(request.getEnvironment()));
        IntegerResponse response = jenkinsClient.api().jobsApi().buildWithParameters(jenkinsProperties.getFolderPath(), request.getJobName(), properties);
        if (Objects.isNull(response.value())) {
            List<String> errors = response.errors().stream().map(Error::exceptionName).collect(Collectors.toList());
            log.error("Failed to buildWithParameters jobName: [{}], properties: [{}], errors:[{}]", request.getJobName(), properties, errors);
            Assert.notNull(response.value(), "构建Job失败");
        }
    }

    @Override
    public Boolean pipelineExist(PipelineDetailRequest pipelineDetailRequest) {
        JenkinsPipelineDetailRequest request = (JenkinsPipelineDetailRequest) pipelineDetailRequest;
        try {
            JobInfo jobInfo = jenkinsClient.api().jobsApi()
                    .jobInfo(jenkinsProperties.getFolderPath(), request.getJobName());
            return Objects.nonNull(jobInfo);
        } catch (Exception e) {
            log.error("Failed to jobInfo jobName: [{}]", request.getJobName());
            throw new PipelineOpenApiException(e);
        }
    }

    @Override
    public String nextBuildId(PipelineNextBuildIdRequest pipelineNextBuildIdRequest) {
        JenkinsNextBuildIdRequest request = (JenkinsNextBuildIdRequest) pipelineNextBuildIdRequest;
        try {
            JobInfo jobInfo = jenkinsClient.api().jobsApi()
                    .jobInfo(jenkinsProperties.getFolderPath(), request.getJobName());
            Assert.notNull(jobInfo, "Failed to get nextBuildId [" + request.getJobName() + "]");
            return String.valueOf(jobInfo.nextBuildNumber());
        } catch (Exception e) {
            log.error("Failed to jobInfo jobName: [{}]", request.getJobName());
            throw new PipelineOpenApiException(e);
        }
    }

    @Override
    public void createPipeline(PipelineCreateRequest pipelineCreateRequest) {
        JenkinsCreatePipelineRequest request = (JenkinsCreatePipelineRequest) pipelineCreateRequest;
        String configXml = configBuilder.createConfig(request.getJobName(),
                jenkinsProperties.getImageRepositoryHost(), jenkinsProperties.getImageRepositoryOrg(),
                jenkinsProperties.getImageRepositoryUsername(), jenkinsProperties.getImageRepositoryPassword());
        RequestStatus response = jenkinsClient.api().jobsApi()
                .create(jenkinsProperties.getFolderPath(), request.getJobName(), configXml);
        if (Boolean.FALSE.equals(response.value())) {
            List<String> errors = response.errors().stream().map(Error::exceptionName).collect(Collectors.toList());
            log.error("Failed to createJob, jobName: [{}], errors:[{}]", request.getJobName(), errors);
        }
        Assert.isTrue(response.value(), "创建Job失败");
    }

    @Override
    public PipelineBuildInfoDTO buildInfo(PipelineBuildInfoRequest pipelineBuildInfoRequest) {
        JenkinsPipelineBuildInfoRequest request = (JenkinsPipelineBuildInfoRequest) pipelineBuildInfoRequest;
        try {
            BuildInfo buildInfo = jenkinsClient.api().jobsApi().buildInfo(jenkinsProperties.getFolderPath(),
                    request.getJobName(), Integer.parseInt(request.getBuildNumber()));
            return new JenkinsPipelineBuildInfoDTO(buildInfo.building(),
                    buildInfo.result(), buildInfo.description());
        } catch (Exception e) {
            log.error("Failed to buildInfo jobName: [{}], buildNumber:[{}]", request.getJobName(), request.getBuildNumber());
            throw new PipelineOpenApiException(e);
        }
    }
}
