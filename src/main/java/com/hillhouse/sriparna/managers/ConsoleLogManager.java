package com.hillhouse.sriparna.managers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.interfaces.LogManager;


public class ConsoleLogManager implements LogManager {
    public void addToLog(String log){
        System.out.println(log);
    }
}
