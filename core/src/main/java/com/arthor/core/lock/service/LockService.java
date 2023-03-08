package com.arthor.core.lock.service;

public interface LockService {

    /**
     * 获取锁
     *
     * @param lockEntry
     * @return
     */
    Boolean tryLock(String lockEntry);

    /**
     * 释放锁
     *
     * @param lockEntry
     * @return
     */
    Boolean releaseLock(String lockEntry);

}
