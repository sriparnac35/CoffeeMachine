package com.hillhouse.sriparna.interfaces;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

public interface Deserializer {
    <T> T deserialize(Fetchable fetchable) throws Exception;
}
