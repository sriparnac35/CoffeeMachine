package com.hillhouse.sriparna.models;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@AllArgsConstructor
@Getter
public final class Ingredient {
    @NonNull @JsonProperty("id")
    private final Integer id;

    @NonNull @JsonProperty("name")
    private final String name;
}
