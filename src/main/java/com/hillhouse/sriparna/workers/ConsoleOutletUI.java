package com.hillhouse.sriparna.workers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.hillhouse.sriparna.config.CoffeeMachineOutletConfig;

public class ConsoleOutletUI extends OutletUI {
    public ConsoleOutletUI(CoffeeMachineOutletConfig outletConfig) {
        super(outletConfig);
    }

    @Override
    protected void onProgressStart() {
        System.out.println("starting progress");
    }

    @Override
    protected void onProgressEnd() {
        System.out.println("ending progress");
    }

    @Override
    protected void onError() {
        System.out.println("error providing drink");
    }

    @Override
    protected void pour() {
        System.out.println("pouring..");
    }
}
