package com.hillhouse.sriparna;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.config.CoffeeMachineConfig;
import com.hillhouse.sriparna.config.CoffeeMachineOutletConfig;
import com.hillhouse.sriparna.enums.ConfigType;
import com.hillhouse.sriparna.interfaces.ConfigFetcher;
import com.hillhouse.sriparna.interfaces.Deserializer;
import com.hillhouse.sriparna.interfaces.Initializable;
import com.hillhouse.sriparna.interfaces.LogManager;
import com.hillhouse.sriparna.managers.OutletManager;
import com.hillhouse.sriparna.models.Drink;
import com.hillhouse.sriparna.workers.Outlet;
import javafx.util.Pair;
import lombok.NonNull;

import java.util.Map;
import java.util.stream.Collectors;

public class CoffeeMachine implements Initializable {
    @Inject private OutletManager outletManager;
    @Inject private LogManager logManager;
    @Inject private ConfigFetcher configFetcher;
    @Inject private Deserializer deserializer;
    private Map<Integer, CoffeeMachineOutletConfig> outletConfig;
    private Map<Integer, Outlet> outlets;

    public void prepare(@NonNull Integer id){
        Outlet outlet = outlets.get(id);
        if (outlet == null){
            logManager.addToLog("invalid input selected");
            return;
        }
        outlet.prepare();
    }

    public Map<Integer, Drink> getAvailableDrinks(){
        return outletConfig.entrySet().stream().map(item -> new Pair<>(item.getKey(), item.getValue().getDrink()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public void initialize() throws Exception {
        outletConfig = deserializer.<CoffeeMachineConfig>deserialize(configFetcher.fetchConfig(ConfigType.COFFEE_MACHINE, 0)).getOutletConfig();
        outletConfig.forEach((id, config) -> {
            Outlet outlet = outletManager.createOutletForConfig(config);
            try {
                outlet.initialize();
            } catch (Exception e) {
                logManager.addToLog("unable to initialize outlet " + outlet);
            }
            outlets.put(id, outlet);
        });
    }

    @Override
    public void destroy() throws Exception {
        outlets.forEach((id, outlet) -> {
            try {
                outlet.destroy();
            } catch (Exception e) {
                logManager.addToLog("unable to destroy outlet " + outlet);
            }
        });
    }
}
