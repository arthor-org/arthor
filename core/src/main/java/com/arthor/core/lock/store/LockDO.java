package com.arthor.core.lock.store;

public class LockDO {

    /**
     * 锁
     */
    private String lockEntry;

    public String getLockEntry() {
        return lockEntry;
    }

    public void setLockEntry(String lockEntry) {
        this.lockEntry = lockEntry;
    }
}
