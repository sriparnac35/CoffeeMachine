package com.hillhouse.sriparna.models;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@AllArgsConstructor
@Data
public final class Recipe {
    @NonNull @JsonProperty("id")
    private final Integer id;

    @NonNull @JsonProperty("name")
    private final String name;

    @NonNull @JsonProperty("recipeItems")
    private final List<RecipeItem> recipeItems;

    @AllArgsConstructor
    @Data
    public static class RecipeItem{
        @JsonProperty("ingredient")
        private Ingredient ingredient;

        @JsonProperty("quantity_in_ml")
        private float quantityInML;
    }
}
