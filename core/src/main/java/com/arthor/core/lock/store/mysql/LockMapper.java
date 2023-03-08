package com.arthor.core.lock.store.mysql;

import com.arthor.core.lock.store.LockDO;

public interface LockMapper {
    int delete(LockDO entity);

    int save(LockDO entity);
}
