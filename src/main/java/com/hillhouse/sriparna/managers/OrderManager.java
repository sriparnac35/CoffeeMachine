package com.hillhouse.sriparna.managers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.enums.DrinkOrderStatus;
import com.hillhouse.sriparna.interfaces.ListDataSource;
import com.hillhouse.sriparna.interfaces.TimestampProvider;
import com.hillhouse.sriparna.interfaces.UUIDGenerator;
import com.hillhouse.sriparna.models.Order;
import lombok.NonNull;

public class OrderManager {
    @Inject private ListDataSource<Order> listDataSource;
    @Inject private ConsoleLogManager logManager;
    @Inject private UUIDGenerator idGenerator;
    @Inject private TimestampProvider timestampProvider;

    public synchronized Order createNewOrder(@NonNull Integer drinkID){
        Order order = new Order(idGenerator.nextID(), timestampProvider.currentTimestamp(), drinkID);
        order.setOrderStatus(DrinkOrderStatus.SUBMITTED);
        listDataSource.add(order);
        logManager.addToLog(order.toString());
        return order;
    }
}
