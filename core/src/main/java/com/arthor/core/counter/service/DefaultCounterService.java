package com.arthor.core.counter.service;

import com.arthor.core.counter.store.CounterDO;
import com.arthor.core.counter.store.CounterStore;

public class DefaultCounterService implements CounterService {

    private final CounterStore counterStore;

    public DefaultCounterService(CounterStore counterStore) {
        this.counterStore = counterStore;
    }

    @Override
    public Long getAndIncrement() {
        CounterDO counterDO = new CounterDO();
        counterStore.getAndIncrement(counterDO);
        return counterDO.getId();
    }
}
