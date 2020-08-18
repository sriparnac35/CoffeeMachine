package com.hillhouse.sriparna.config;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CoffeeMachineOutletConfig {
    @JsonProperty("outlet_id")
    private final Integer outletID;

    @JsonProperty("drink_id")
    private final Integer drinkID;

    @JsonProperty("serving_count")
    private int servingCount;
}
