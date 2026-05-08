package com.likerhood.design.workshop.impl;

import com.likerhood.design.redis.cluster.IIR;
import com.likerhood.design.workshop.ICacheAdapter;

import java.util.concurrent.TimeUnit;

public class IIRCacheAdapter implements ICacheAdapter{

    private IIR iir = new IIR();
    @Override
    public String get(String key) {
        return iir.get(key);
    }

    @Override
    public void set(String key, String value) {
        iir.set(key, value);
    }

    @Override
    public void set(String key, String value, long timeout, TimeUnit timeUnit) {
        iir.setExpire(key, value, timeout, timeUnit);
    }

    @Override
    public void del(String key) {
        iir.del(key);
    }
}
