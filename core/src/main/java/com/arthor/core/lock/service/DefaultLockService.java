package com.arthor.core.lock.service;

import com.arthor.core.lock.store.LockDO;
import com.arthor.core.lock.store.LockStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.arthor.core.common.utils.Assert;

public class DefaultLockService implements LockService {
    private final static Logger log = LoggerFactory.getLogger(DefaultLockService.class);
    private final LockStore lockStore;

    public DefaultLockService(LockStore lockStore) {
        this.lockStore = lockStore;
    }

    @Override
    public Boolean tryLock(String lockEntry) {
        try {
            LockDO entity = new LockDO();
            entity.setLockEntry(lockEntry);
            Assert.isTrue(lockStore.save(entity), "获取锁失败");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.warn("Failed to tryLock entry:[{}}", lockEntry, e);
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean releaseLock(String lockEntry) {
        try {
            LockDO entity = new LockDO();
            entity.setLockEntry(lockEntry);
            Assert.isTrue(lockStore.delete(entity), "释放锁失败");
            return Boolean.TRUE;
        } catch (Exception e) {
            log.warn("Failed to releaseLock entry:[{}}", lockEntry, e);
        }
        return Boolean.FALSE;
    }

}
