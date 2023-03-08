package com.arthor.core.counter.store;

public interface CounterStore {
    void getAndIncrement(CounterDO counterDO);
}
