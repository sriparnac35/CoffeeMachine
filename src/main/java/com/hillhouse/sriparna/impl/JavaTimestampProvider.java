package com.hillhouse.sriparna.impl;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.interfaces.TimestampProvider;

public class JavaTimestampProvider implements TimestampProvider {
    @Override
    public Long currentTimestamp() {
        return System.currentTimeMillis();
    }
}
