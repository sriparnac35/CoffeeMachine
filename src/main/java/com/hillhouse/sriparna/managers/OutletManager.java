package com.hillhouse.sriparna.managers;

/* Copyright (c) 2020. 
Created by sriparna.c on 19/08/20 */

import com.hillhouse.sriparna.config.CoffeeMachineOutletConfig;
import com.hillhouse.sriparna.workers.ConsoleOutlet;
import com.hillhouse.sriparna.workers.Outlet;

public class OutletManager {

    public Outlet createOutletForConfig(CoffeeMachineOutletConfig outletConfig){
        return new ConsoleOutlet(outletConfig);
    }

}
