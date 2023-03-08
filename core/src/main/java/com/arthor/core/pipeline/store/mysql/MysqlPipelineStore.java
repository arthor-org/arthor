package com.arthor.core.pipeline.store.mysql;

import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.pipeline.model.ListPipelineRequest;
import com.arthor.core.pipeline.store.PipelineDO;
import com.arthor.core.pipeline.store.PipelineStore;

import java.util.List;

public class MysqlPipelineStore implements PipelineStore {
    private final PipelineMapper pipelineMapper;

    public MysqlPipelineStore(PipelineMapper pipelineMapper) {
        this.pipelineMapper = pipelineMapper;
    }

    @Override
    public Boolean create(PipelineDO entity) {
        return SqlHelper.retBool(pipelineMapper.save(entity));
    }

    @Override
    public Boolean update(PipelineDO entity) {
        return SqlHelper.retBool(pipelineMapper.update(entity));
    }

    @Override
    public PipelineDO findById(Long id) {
        return pipelineMapper.findById(id);
    }

    @Override
    public List<PipelineDO> listByCondition(ListPipelineRequest request) {
        return pipelineMapper.list(request);
    }
}
