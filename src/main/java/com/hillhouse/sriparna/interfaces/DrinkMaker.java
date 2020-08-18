package com.hillhouse.sriparna.interfaces;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import lombok.NonNull;

import java.util.Map;

public interface DrinkMaker {
    void make(@NonNull Integer drinkID, @NonNull Map<Integer, Integer> reservationIDs) throws Exception;
}
