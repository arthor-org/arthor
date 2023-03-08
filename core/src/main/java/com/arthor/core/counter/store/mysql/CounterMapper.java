package com.arthor.core.counter.store.mysql;

import com.arthor.core.counter.store.CounterDO;

public interface CounterMapper {

    /**
     * 获取并且自增
     *
     * @return
     */
    Long getAndIncrement(CounterDO counterDO);

}
