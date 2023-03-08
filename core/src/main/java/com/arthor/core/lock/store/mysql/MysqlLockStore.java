package com.arthor.core.lock.store.mysql;

import com.arthor.core.common.utils.SqlHelper;
import com.arthor.core.lock.store.LockDO;
import com.arthor.core.lock.store.LockStore;

public class MysqlLockStore implements LockStore {

    private final LockMapper lockMapper;

    public MysqlLockStore(LockMapper lockMapper) {
        this.lockMapper = lockMapper;
    }


    @Override
    public Boolean save(LockDO entity) {
        return SqlHelper.retBool(lockMapper.save(entity));
    }

    @Override
    public Boolean delete(LockDO entity) {
        return SqlHelper.retBool(lockMapper.delete(entity));
    }
}
