package com.arthor.core.pipeline.service;

import com.arthor.core.common.enumeration.PipelineStatusEnum;
import com.arthor.core.common.enumeration.PipelineTypeEnum;
import com.arthor.core.common.utils.Assert;
import com.arthor.core.pipeline.DefaultPipeline;
import com.arthor.core.pipeline.Pipeline;
import com.arthor.core.pipeline.model.CreatePipelineRequest;
import com.arthor.core.pipeline.model.ListPipelineRequest;
import com.arthor.core.pipeline.model.UpdatePipelineRequest;
import com.arthor.core.pipeline.store.PipelineDO;
import com.arthor.core.pipeline.store.PipelineStore;

import java.util.List;
import java.util.stream.Collectors;

public class DefaultPipelineService implements PipelineService {

    private final PipelineStore pipelineStore;

    public DefaultPipelineService(PipelineStore pipelineStore) {
        this.pipelineStore = pipelineStore;
    }

    @Override
    public Long create(CreatePipelineRequest request) {
        PipelineDO entity = new PipelineDO();
        entity.setJobName(request.getJobName());
        entity.setType(PipelineTypeEnum.JENKINS);
        entity.setStatus(PipelineStatusEnum.CREATING);
        Assert.isTrue(pipelineStore.create(entity), "保存流水线失败");
        return entity.getId();
    }

    @Override
    public void update(UpdatePipelineRequest request) {
        PipelineDO pipelineDO = new PipelineDO();
        pipelineDO.setId(request.getId());
        pipelineDO.setStatus(request.getStatus());
        Assert.isTrue(pipelineStore.update(pipelineDO), "修改流水线失败");
    }

    @Override
    public Pipeline findById(Long id) {
        PipelineDO entity = pipelineStore.findById(id);
        Assert.notNull(entity, "无法获取流水线信息,[ " + id + " ]");
        return convert(entity);
    }

    @Override
    public List<Pipeline> listByCondition(ListPipelineRequest request) {
        return pipelineStore.listByCondition(request).stream()
                .map(this::convert).collect(Collectors.toList());
    }

    private Pipeline convert(PipelineDO entity) {
        DefaultPipeline pipeline = new DefaultPipeline();
        pipeline.setId(entity.getId());
        pipeline.setJobName(entity.getJobName());
        pipeline.setType(entity.getType());
        pipeline.setStatus(entity.getStatus());
        return pipeline;
    }

}
