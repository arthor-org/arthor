package com.arthor.core.build.store.mysql;

import com.arthor.core.build.model.ListBuildRecordRequest;
import com.arthor.core.build.store.BuildRecordDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 构建记录表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2022-11-24
 */
public interface BuildRecordMapper {



    /**
     * 自增检查次数
     *
     * @param id
     * @return
     */
    int increaseNumberOfCheck(@Param("id") Long id);

    BuildRecordDO findById(Long id);

    int save(BuildRecordDO entity);

    int update(BuildRecordDO entity);

    List<BuildRecordDO> list(ListBuildRecordRequest request);
}
