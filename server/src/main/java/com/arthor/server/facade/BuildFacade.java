package com.arthor.server.facade;

import com.arthor.server.model.dto.BuildRecordDTO;
import com.arthor.server.model.param.build.BuildParam;
import com.arthor.server.model.param.build.ListBuildRecordParam;

import java.util.List;

public interface BuildFacade {

    /**
     * 构建
     *
     * @param param
     */
    Boolean build(BuildParam param);

    /**
     * 构建记录列表
     *
     * @param param
     * @return
     */
    List<BuildRecordDTO> listByCondition(ListBuildRecordParam param);

    /**
     * 构建信息检查
     */
    void checkBuildInfo();


}
