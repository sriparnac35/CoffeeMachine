package com.hillhouse.sriparna.models;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@AllArgsConstructor
@Data
public class Drink {
    @NonNull @JsonProperty("id")
    private final Integer drinkID;

    @NonNull @JsonProperty("recipe")
    private final Recipe recipe;
}
