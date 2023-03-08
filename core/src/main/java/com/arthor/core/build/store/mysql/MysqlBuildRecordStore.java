package com.arthor.core.build.store.mysql;

import com.arthor.core.build.model.ListBuildRecordRequest;
import com.arthor.core.build.store.BuildRecordDO;
import com.arthor.core.build.store.BuildRecordStore;
import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.build.store.mysql.BuildRecordMapper;

import java.util.List;

public class MysqlBuildRecordStore implements BuildRecordStore {

    private final BuildRecordMapper buildRecordMapper;

    public MysqlBuildRecordStore(BuildRecordMapper buildRecordMapper) {
        this.buildRecordMapper = buildRecordMapper;
    }

    @Override
    public Boolean create(BuildRecordDO entity) {
        return SqlHelper.retBool(buildRecordMapper.save(entity));
    }

    @Override
    public Boolean increaseNumberOfCheck(Long id) {
        return SqlHelper.retBool(buildRecordMapper.increaseNumberOfCheck(id));
    }

    @Override
    public Boolean update(BuildRecordDO entity) {
        return SqlHelper.retBool(buildRecordMapper.update(entity));
    }

    @Override
    public List<BuildRecordDO> listByCondition(ListBuildRecordRequest request) {
        return buildRecordMapper.list(request);
    }

    @Override
    public BuildRecordDO findById(Long id) {
        return buildRecordMapper.findById(id);
    }
}
