package com.hillhouse.sriparna.managers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.interfaces.UUIDGenerator;

public class IDManager {
    @Inject private UUIDGenerator uuidGenerator;
    public Integer getNextID(){
        return uuidGenerator.nextID();
    }
}
