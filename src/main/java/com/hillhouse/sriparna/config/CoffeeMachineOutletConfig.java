package com.hillhouse.sriparna.config;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hillhouse.sriparna.models.Drink;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CoffeeMachineOutletConfig {
    @JsonProperty("outlet_id")
    private final Integer outletID;

    @JsonProperty("drink")
    private final Drink drink;

    @JsonProperty("serving_count")
    private int servingCount;
}
