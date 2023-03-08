package com.arthor.core.pipeline.store.mysql;

import com.arthor.core.pipeline.model.ListPipelineRequest;
import com.arthor.core.pipeline.store.PipelineDO;

import java.util.List;

public interface PipelineMapper {

    int save(PipelineDO pipelineDO);

    PipelineDO findById(Long id);

    List<PipelineDO> list(ListPipelineRequest param);

    int update(PipelineDO pipelineDO);

}
