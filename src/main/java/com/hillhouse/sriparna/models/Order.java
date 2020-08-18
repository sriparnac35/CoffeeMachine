package com.hillhouse.sriparna.models;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.enums.DrinkOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Data
public class Order {
    private final Integer orderID;
    private final Long timestamp;
    private final Integer drinkID;
    private DrinkOrderStatus orderStatus;
}
