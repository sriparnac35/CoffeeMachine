package com.hillhouse.sriparna.impl;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.interfaces.UUIDGenerator;

import java.util.concurrent.atomic.AtomicInteger;

public class IncrementalIDGenerator implements UUIDGenerator {
    private final AtomicInteger current;
    public IncrementalIDGenerator(){
        this.current = new AtomicInteger();
    }
    @Override
    public Integer nextID() {
        return current.incrementAndGet();
    }
}
