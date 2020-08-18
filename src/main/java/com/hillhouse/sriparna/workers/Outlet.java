package com.hillhouse.sriparna.workers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.config.CoffeeMachineOutletConfig;
import com.hillhouse.sriparna.enums.DrinkOrderStatus;
import com.hillhouse.sriparna.interfaces.Initializable;
import com.hillhouse.sriparna.interfaces.LogManager;
import com.hillhouse.sriparna.managers.OrderManager;
import com.hillhouse.sriparna.models.Order;
import io.reactivex.disposables.Disposable;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.atomic.AtomicBoolean;

@RequiredArgsConstructor
public abstract class Outlet implements Initializable {
    @Inject private OrderManager orderManager;
    @Inject private LogManager logManager;
    private final CoffeeMachineOutletConfig outletConfig;
    private OutletWorker outletWorker;
    private AtomicBoolean isBusy ;

    @Override
    public void initialize() throws Exception{
        isBusy = new AtomicBoolean();
        outletWorker = new OutletWorker(outletConfig);
        outletWorker.initialize();
    }

    @Override
    public void destroy() throws Exception{
        outletWorker.destroy();
    }

    public boolean isBusy(){
        return isBusy.get();
    }

    public synchronized void prepare(){
        if (isBusy()){
            return;
        }
        onProgressStart();
        isBusy.set(true);
        Order order = orderManager.createNewOrder(outletConfig.getDrink().getDrinkID());
        outletWorker.serve(order).subscribe(item -> {}, throwable -> onOrderServeFailed(order, throwable),
                () -> onOrderServeSuccess(order), Disposable::dispose);
    }

    private void onOrderServeFailed(Order order, Throwable throwable){
        onError();
        onProgressEnd();
    }

    private void onOrderServeSuccess(Order order){
        pour();
        onProgressEnd();
        order.setOrderStatus(DrinkOrderStatus.DELIVERED);
        logManager.addToLog(order.toString());
    }

    protected abstract void onProgressStart();
    protected abstract void onProgressEnd();
    protected abstract void onError();
    protected abstract void pour();
}
