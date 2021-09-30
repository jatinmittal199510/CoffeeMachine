package com.pls.commands;

import com.pls.exceptions.BeverageNotFoundInInventoryException;
import com.pls.exceptions.IngredientNotFoundInInventoryException;
import com.pls.models.CoffeeMachine;

import java.util.Arrays;
import java.util.List;

public class Command {
    private static final String SPACE = " ";
    private static final String REFILL = "refill";
    private static final String REFILL_ALL = "refill_all";
    private static final String SERVE = "serve";
    private static final String SHOW_INGREDIENTS_RUNNING_LOW = "show_ingredients_running_low";

    private String command;
    private String param;


    public Command(String command){
        List<String> tokens = Arrays.asList(command.split(SPACE));
        this.command = tokens.get(0).toLowerCase();
        if(tokens.size() > 1){
            this.param = tokens.get(1);
        }
    }

    public void execute(CoffeeMachine coffeeMachine) throws IngredientNotFoundInInventoryException,
                                                            BeverageNotFoundInInventoryException {
        if(command.equals(REFILL)){
            coffeeMachine.refill(param);
        }
        else if(command.equals(REFILL_ALL)){
            coffeeMachine.refillAll();
        }
        else if(command.equals(SERVE)){
            coffeeMachine.serveBeverage(param);
        }
        else if(command.equals(SHOW_INGREDIENTS_RUNNING_LOW)){
            coffeeMachine.showIngredientsRunningLow();
        }
    }
}
