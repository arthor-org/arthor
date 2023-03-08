package com.arthor.core.build.store;

import com.arthor.core.build.model.ListBuildRecordRequest;

import java.util.List;

public interface BuildRecordStore {

    /**
     * 构建
     *
     * @param entity
     * @return
     */
    Boolean create(BuildRecordDO entity);

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
     * @param entity
     * @return
     */
    Boolean update(BuildRecordDO entity);

    /**
     * 构建记录列表
     *
     * @param request
     * @return
     */
    List<BuildRecordDO> listByCondition(ListBuildRecordRequest request);

    /**
     * 根据id查询
     *
     * @param id
     * @return
     */
    BuildRecordDO findById(Long id);
}
