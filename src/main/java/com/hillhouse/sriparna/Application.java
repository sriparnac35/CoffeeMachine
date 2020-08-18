package com.hillhouse.sriparna;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.hillhouse.sriparna.interfaces.Initializable;
import com.hillhouse.sriparna.models.Drink;

import java.util.Map;
import java.util.Scanner;

public class Application implements Initializable {
    @Inject private CoffeeMachine coffeeMachine;
    private Map<Integer, Drink> servableDrinks;
    private Scanner scanner;

    public static void main( String[] args ) throws Exception {
        Injector injector = Guice.createInjector(new Module());
        Application application = injector.getInstance(Application.class);
        application.initialize();
        application.execute();
    }

    public void execute(){
        while(true){
            printAvailableDrinks();
            int drinkToServe = scanner.nextInt();
            coffeeMachine.prepare(drinkToServe);
        }
    }

    private void printAvailableDrinks(){
        System.out.println();
        System.out.println("The following drinks can be currently served");
        servableDrinks.forEach((outletID, drink) -> System.out.println(outletID + " : " + drink.getDrinkName()));
        System.out.println("Please enter the id of the drink to serve : ");
    }

    @Override
    public void initialize() throws Exception {
        coffeeMachine.initialize();
        servableDrinks = coffeeMachine.getAvailableDrinks();
        scanner = new Scanner(System.in);
    }

    @Override
    public void destroy() throws Exception {

    }
}
