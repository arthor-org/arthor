package com.arthor.core.pipeline.store;

import com.arthor.core.pipeline.model.ListPipelineRequest;

import java.util.List;

public interface PipelineStore {
    /**
     * 保存流水线信息
     *
     * @param entity
     * @return
     */
    Boolean create(PipelineDO entity);

    /**
     * 修改
     *
     * @param entity
     * @return
     */
    Boolean update(PipelineDO entity);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    PipelineDO findById(Long id);

    /**
     * 根据条件查询
     *
     * @param request
     * @return
     */
    List<PipelineDO> listByCondition(ListPipelineRequest request);

}


