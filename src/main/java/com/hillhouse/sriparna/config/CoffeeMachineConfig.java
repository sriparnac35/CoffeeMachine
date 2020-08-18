package com.hillhouse.sriparna.config;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@AllArgsConstructor
@Data
public class CoffeeMachineConfig {
    @JsonProperty("id")
    private final Integer id;

    @JsonProperty("outlet_count")
    private int outletCount;

    @JsonProperty("outlet_config")
    private Map<Integer, CoffeeMachineOutletConfig> outletConfig;
}
