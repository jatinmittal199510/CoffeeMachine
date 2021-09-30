package com.pls.models;

import com.pls.Display;
import com.pls.exceptions.BeverageNotFoundInInventoryException;
import com.pls.exceptions.IngredientNotFoundInInventoryException;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class CoffeeMachine {
    private AtomicInteger totalOutlets;
    @Setter private AtomicInteger freeOutlets;
    private Inventory inventory;

    public CoffeeMachine(final AtomicInteger totalOutlets, final Inventory inventory){
        this.totalOutlets = totalOutlets;
        this.freeOutlets = totalOutlets;
        this.inventory = inventory;
    }

    /**
     * This method checks if the quantity required by the requested beverage is available in the inventory
     * @param beverageName
     * @return
     * @throws IngredientNotFoundInInventoryException
     * @throws BeverageNotFoundInInventoryException
     */
    private boolean isRequiredQuantitiesAvailable(String beverageName) throws IngredientNotFoundInInventoryException,
                                                                              BeverageNotFoundInInventoryException {
        Beverage beverage = inventory.getBeverage(beverageName);
        for(Map.Entry<String, Double> entry : beverage.getQuantities().entrySet()){
            Ingredient ingredient = inventory.getIngredient(entry.getKey());
            if(ingredient.getIngredientCurrentQuantity() < entry.getValue()){
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks if there are any free outlets available
     * @return
     */
    private boolean isFreeOutletAvailable(){

        return freeOutlets.get() > 0;
    }

    /**
     * This method will serve the requested beverage if there are any free outlet available
     * and the required quantities are present in the inventory
     * @param beverageName
     * @throws IngredientNotFoundInInventoryException
     * @throws BeverageNotFoundInInventoryException
     */
    public synchronized void serveBeverage(@NonNull String beverageName) throws IngredientNotFoundInInventoryException,
                                                                                BeverageNotFoundInInventoryException {
        if(!isFreeOutletAvailable()){
            Display.freeOutletNotAvailable(beverageName);
            return;
        }

        if(!isRequiredQuantitiesAvailable(beverageName)){
            Display.beverageIngredientsQuantityNotAvailable(beverageName);
            return;
        }

        freeOutlets.getAndDecrement();
        Beverage beverage = inventory.getBeverage(beverageName);

        for(Map.Entry<String, Double> entry : beverage.getQuantities().entrySet()){
            String ingredientName = entry.getKey();
            Ingredient ingredient = inventory.getIngredient(ingredientName);
            Double leftQuantity = ingredient.getIngredientCurrentQuantity() - entry.getValue();
            ingredient.setIngredientCurrentQuantity(leftQuantity);
            ingredient.updateIngredientIndicatorValue();
        }
        Display.beverageServed(beverageName);
        freeOutlets.getAndIncrement();
    }

    /**
     * This function fills the given ingredient name to its full capacity
     * @param ingredientName
     * @throws IngredientNotFoundInInventoryException : throws exception if the ingredientName is not present in inventory
     *
     */
    public void refill(@NonNull String ingredientName) throws IngredientNotFoundInInventoryException {
        inventory.getIngredient(ingredientName).refill();
        Display.ingredientsRefilled(ingredientName);
    }

    /**
     *
     *  This function will refill all the ingredients to its full capacity.
     *  If any ingredient is already full, then the appropriate message will be displayed.
     */
    public void refillAll(){
        inventory.refillAll();
        Display.allIngredientsRefilled();
    }

    /**
     * This function shows all the ingredients which are running low.
     * Definition of running low is defined as - [ current_capacity < 0.25 * total_capacity of the ingredient ]
     */
    public void showIngredientsRunningLow(){
        List<String> ingredientsRunningLow = inventory.getIngredientsRunningLow();
        if(ingredientsRunningLow.size() == 0){
            Display.noIngredientIsRunningLow();
            return;
        }
        Display.ingredientsRunningLow(ingredientsRunningLow);
    }
}
