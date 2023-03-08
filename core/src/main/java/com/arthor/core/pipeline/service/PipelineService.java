package com.arthor.core.pipeline.service;

import com.arthor.core.pipeline.Pipeline;
import com.arthor.core.pipeline.model.CreatePipelineRequest;
import com.arthor.core.pipeline.model.ListPipelineRequest;
import com.arthor.core.pipeline.model.UpdatePipelineRequest;

import java.util.List;

public interface PipelineService {

    /**
     * 保存流水线信息
     *
     * @param request
     * @return
     */
    Long create(CreatePipelineRequest request);

    /**
     * 批量修改
     *
     * @param request
     * @return
     */
    void update(UpdatePipelineRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    Pipeline findById(Long id);

    /**
     * 根据条件查询
     *
     * @param request
     * @return
     */
    List<Pipeline> listByCondition(ListPipelineRequest request);

}
