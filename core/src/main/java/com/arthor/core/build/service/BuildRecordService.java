package com.arthor.core.build.service;

import com.arthor.core.build.BuildRecord;
import com.arthor.core.build.model.CreateBuildRequest;
import com.arthor.core.build.model.ListBuildRecordRequest;

import java.util.List;

/**
 * <p>
 * 构建记录表 服务类
 * </p>
 *
 * @author 
 * @since 2022-11-24
 */
public interface BuildRecordService {

    /**
     * 构建
     *
     * @param request
     * @return
     */
    Long createBuildRecord(CreateBuildRequest request);

    /**
     * 自增检查次数
     *
     * @param id
     * @return
     */
    Boolean increaseNumberOfCheck(Long id);

    /**
     * 批量修改
     *
     * @param entityList
     * @return
     */
    Boolean updateBuildRecordBatch(List<BuildRecord> entityList);

    /**
     * 构建记录列表
     *
      * @param request
     * @return
     */
    List<BuildRecord> listByCondition(ListBuildRecordRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BuildRecord findById(Long id);

}
