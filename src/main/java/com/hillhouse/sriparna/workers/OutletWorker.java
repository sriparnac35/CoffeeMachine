package com.hillhouse.sriparna.workers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.config.CoffeeMachineOutletConfig;
import com.hillhouse.sriparna.enums.ConfigType;
import com.hillhouse.sriparna.enums.DrinkOrderStatus;
import com.hillhouse.sriparna.exceptions.InsufficientIngredientsException;
import com.hillhouse.sriparna.interfaces.*;
import com.hillhouse.sriparna.models.Drink;
import com.hillhouse.sriparna.managers.IngredientStock;
import com.hillhouse.sriparna.models.Order;
import com.hillhouse.sriparna.models.Recipe;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RequiredArgsConstructor
public class OutletWorker implements Initializable {
    @Inject private ConfigFetcher configFetcher;
    @Inject private Deserializer deserializer;
    @Inject private IngredientStock ingredientStock;
    @Inject private DrinkMaker drinkMaker;
    private final CoffeeMachineOutletConfig outletConfig;
    private Drink drinkToServe;
    private ExecutorService executorService;
    private Scheduler scheduler;

    public Observable<Order> serve(Order order){
        return Observable.create(new DrinkObservable(order)).observeOn(scheduler);
    }

    @Override
    public void initialize() throws Exception {
        Fetchable configFetchable = configFetcher.fetchConfig(ConfigType.OUTLET, outletConfig.getDrink().getDrinkID());
        drinkToServe = deserializer.deserialize(configFetchable);
        if (drinkToServe == null){
            throw new IllegalArgumentException("drink config with Id " + outletConfig.getDrink().getDrinkID() + " could not be fetched ");
        }
        executorService = Executors.newSingleThreadExecutor();
        scheduler = Schedulers.from(executorService);
        scheduler.start();
    }

    @Override
    public void destroy() throws Exception {
        scheduler.shutdown();
        executorService.shutdown();
    }

    class DrinkObservable implements ObservableOnSubscribe<Order> {
        private final Order order;
        private final Map<Integer, Integer> reservations;
        private boolean successFullyBlocked;
        private ObservableEmitter<Order> observableEmitter ;

        public DrinkObservable(Order order) {
            this.order = order;
            this.successFullyBlocked = true;
            this.reservations = new HashMap<>();
        }

        @Override
        public void subscribe(ObservableEmitter<Order> observableEmitter) throws Exception {
            this.observableEmitter = observableEmitter;
            for (Recipe.RecipeItem item : drinkToServe.getRecipe().getRecipeItems()) {
                Integer ingredientID = item.getIngredient().getId();
                Float quantity = item.getQuantityInML();
                Integer reservationID = ingredientStock.blockIngredient(ingredientID, quantity);
                if (reservationID == null) {
                    successFullyBlocked = false;
                    break;
                } else {
                    reservations.put(ingredientID, reservationID);
                }
            }
            handleReservations();
        }
        private void handleReservations() {
            if (successFullyBlocked) {
                handleReservationSuccessful();
            } else {
                handleReservationFailed();
            }
        }

        private void handleReservationSuccessful() {
            try {
                drinkMaker.make(order.getDrinkID(), reservations);
                order.setOrderStatus(DrinkOrderStatus.IN_PROGRESS);
                observableEmitter.onComplete();
            } catch (Exception e) {
                order.setOrderStatus(DrinkOrderStatus.ERROR);
                observableEmitter.onError(e);
            }
        }

        private void handleReservationFailed() {
            reservations.forEach((key, value) -> ingredientStock.unblockIngredient(key, value));
            order.setOrderStatus(DrinkOrderStatus.ERROR);
            observableEmitter.onError(new InsufficientIngredientsException());
        }
    }
}
