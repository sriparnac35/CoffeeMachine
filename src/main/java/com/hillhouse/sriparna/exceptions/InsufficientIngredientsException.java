package com.hillhouse.sriparna.exceptions;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

public class InsufficientIngredientsException extends Exception {
    @Override
    public String getMessage() {
        return "Insufficient Ingredients Exception";
    }
}
