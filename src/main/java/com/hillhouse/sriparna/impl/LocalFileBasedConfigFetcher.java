package com.hillhouse.sriparna.impl;

/* Copyright (c) 2020. 
Created by sriparna.c on 19/08/20 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hillhouse.sriparna.config.CoffeeMachineConfig;
import com.hillhouse.sriparna.enums.ConfigType;
import com.hillhouse.sriparna.interfaces.ConfigFetcher;
import com.hillhouse.sriparna.interfaces.Fetchable;

import java.io.IOException;

public class LocalFileBasedConfigFetcher implements ConfigFetcher {
    @Override
    public Fetchable fetchConfig(ConfigType configType, Integer id) {
        switch (configType){
            case COFFEE_MACHINE:
                try {
                    return new ObjectMapper().readValue(getClass()
                            .getResource("CoffeeMachineConfig.json"), CoffeeMachineConfig.class);
                } catch (IOException e) {
                    return null;
                }
            default:
                return null;
        }
    }
}
