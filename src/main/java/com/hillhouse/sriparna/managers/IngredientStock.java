package com.hillhouse.sriparna.managers;

/* Copyright (c) 2020. 
Created by sriparna.c on 08/08/20 */

import com.google.inject.Inject;
import com.hillhouse.sriparna.interfaces.DataSource;
import com.hillhouse.sriparna.interfaces.LogManager;
import com.hillhouse.sriparna.interfaces.UUIDGenerator;
import lombok.NonNull;

public class IngredientStock {
    @Inject private DataSource<Integer, Float> ingredientDataSource;
    @Inject private DataSource<Integer, Integer> reservationsDataSource;
    @Inject private DataSource<Integer, Float> blockedIngredientsDataSource;
    @Inject private UUIDGenerator idGenerator;
    @Inject private LogManager logManager;

    public Integer blockIngredient(@NonNull Integer ingredientID, float quantityInML){
        synchronized (ingredientID){
            Float availableQuantity = ingredientDataSource.getValueByKey(ingredientID);
            if (availableQuantity < quantityInML){
                return null;
            }
            Integer reservationID = idGenerator.nextID();
            ingredientDataSource.update(ingredientID, availableQuantity - quantityInML);
            reservationsDataSource.add(reservationID, ingredientID);
            blockedIngredientsDataSource.add(reservationID, quantityInML);
            return reservationID;
        }
    }

    public void unblockIngredient(@NonNull Integer ingredientID, @NonNull Integer reservationID){
        synchronized (ingredientID){
            Integer igID = reservationsDataSource.getValueByKey(reservationID);
            if (igID != ingredientID){
                logManager.addToLog("incorrect arguments for unblock ingredient with reservationid " + reservationID);
                return;
            }
            Float availableQuantity = ingredientDataSource.getValueByKey(ingredientID);
            ingredientDataSource.update(ingredientID, availableQuantity + blockedIngredientsDataSource.getValueByKey(reservationID));
            reservationsDataSource.delete(reservationID);
            blockedIngredientsDataSource.delete(reservationID);
        }
    }

    public void useIngredient(@NonNull Integer ingredientID, @NonNull Integer reservationID){
        synchronized (ingredientID){
            Integer igID = reservationsDataSource.getValueByKey(reservationID);
            if (igID != ingredientID){
                logManager.addToLog("incorrect arguments for use ingredient with reservationid " + reservationID);
                return;
            }
            reservationsDataSource.delete(reservationID);
            blockedIngredientsDataSource.delete(reservationID);
        }
    }

}
