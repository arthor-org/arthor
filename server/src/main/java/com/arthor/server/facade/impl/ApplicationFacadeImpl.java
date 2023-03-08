package com.arthor.server.facade.impl;

import com.arthor.server.model.dto.ApplicationDTO;
import com.arthor.core.application.model.CreateApplicationRequest;
import com.arthor.core.application.model.ListApplicationRequest;
import com.arthor.core.application.service.ApplicationService;
import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.integration.pipeline.jenkins.JenkinsCreatePipelineRequest;
import com.arthor.core.integration.pipeline.jenkins.JenkinsPipelineDetailRequest;
import com.arthor.core.integration.pipeline.model.PipelineDetailRequest;
import com.arthor.core.integration.pipeline.service.PipelineOpenApiService;
import com.arthor.core.pipeline.Pipeline;
import com.arthor.core.pipeline.model.CreatePipelineRequest;
import com.arthor.core.pipeline.model.ListPipelineRequest;
import com.arthor.core.pipeline.model.UpdatePipelineRequest;
import com.arthor.core.pipeline.service.PipelineService;
import com.arthor.server.facade.ApplicationFacade;
import com.arthor.server.model.param.application.CreateApplicationParam;
import com.arthor.server.model.param.application.ListApplicationParam;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationFacadeImpl implements ApplicationFacade {

    private final ApplicationService applicationService;
    private final PipelineService pipelineService;
    private final PipelineOpenApiService pipelineOpenApiService;

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public Boolean createApplication(CreateApplicationParam param) {
        CreatePipelineRequest createPipelineRequest = new CreatePipelineRequest();
        createPipelineRequest.setJobName(param.getName());
        Long pipelineId = pipelineService.create(createPipelineRequest);
        CreateApplicationRequest createApplicationRequest = new CreateApplicationRequest();
        createApplicationRequest.setName(param.getName());
        createApplicationRequest.setDescription(param.getDescription());
        createApplicationRequest.setPipelineId(pipelineId);
        applicationService.create(createApplicationRequest);
        return Boolean.TRUE;
    }

    @Override
    public List<ApplicationDTO> listApplication(ListApplicationParam param) {
        ListApplicationRequest request = new ListApplicationRequest();
        return applicationService.list(request).stream()
                .map(app -> {
                    ApplicationDTO applicationDTO = ApplicationDTO.valueOf(app);
                    if (Objects.isNull(applicationDTO)) {
                        return null;
                    }
                    Pipeline pipeline = pipelineService.findById(app.getPipelineId());
                    applicationDTO.setPipelineStatus(pipeline.getStatus());
                    applicationDTO.setPipelineType(pipeline.getType());
                    return applicationDTO;
                }).filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void checkPipelineInfo() {
        ListPipelineRequest listPipelineRequest = new ListPipelineRequest();
        listPipelineRequest.setStatus(PipelineStatusEnum.CREATING);
        List<Pipeline> pipelineList = pipelineService.listByCondition(listPipelineRequest);
        pipelineList.forEach(pipeline -> {
            PipelineDetailRequest pipelineDetailRequest = new JenkinsPipelineDetailRequest(pipeline.getJobName());
            if (pipelineOpenApiService.pipelineExist(pipelineDetailRequest)) {
                UpdatePipelineRequest updatePipelineRequest = new UpdatePipelineRequest();
                updatePipelineRequest.setId(pipeline.getId());
                updatePipelineRequest.setStatus(PipelineStatusEnum.CREATED);
                pipelineService.update(updatePipelineRequest);
            } else {
                createJenkinsPipelineAsync(pipeline.getJobName());
            }
        });
    }

    private final ExecutorService executorService = Executors.newSingleThreadExecutor(new ThreadFactoryBuilder()
            .setNameFormat("pipeline-task").setDaemon(Boolean.FALSE).build());

    /**
     * 异步创建Jenkins流水线
     */
    private void createJenkinsPipelineAsync(String jobName) {
        JenkinsCreatePipelineRequest jenkinsCreatePipelineRequest = new JenkinsCreatePipelineRequest(jobName);
        executorService.submit(() -> pipelineOpenApiService.createPipeline(jenkinsCreatePipelineRequest));
    }

}
