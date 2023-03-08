package com.arthor.core.counter.store.mysql;

import com.arthor.core.counter.store.CounterDO;
import com.arthor.core.counter.store.CounterStore;
import com.arthor.core.counter.store.mysql.CounterMapper;

public class MysqlCounterStore implements CounterStore {

    private final CounterMapper counterMapper;

    public MysqlCounterStore(CounterMapper counterMapper) {
        this.counterMapper = counterMapper;
    }

    @Override
    public void getAndIncrement(CounterDO counterDO) {
        counterMapper.getAndIncrement(counterDO);
    }
}
