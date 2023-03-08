package com.arthor.core.lock.store;

public interface LockStore {

    Boolean save(LockDO entity);

    Boolean delete(LockDO entity);

}
