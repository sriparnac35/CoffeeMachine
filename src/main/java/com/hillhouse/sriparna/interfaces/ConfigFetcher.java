package com.hillhouse.sriparna.interfaces;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.enums.ConfigType;

public interface ConfigFetcher {
    Fetchable fetchConfig(ConfigType type, Integer id);
}
