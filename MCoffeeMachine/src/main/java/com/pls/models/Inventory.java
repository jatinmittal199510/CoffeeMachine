package com.pls.models;

import com.pls.enums.IngredientIndicatorValue;
import com.pls.exceptions.BeverageNotFoundInInventoryException;
import com.pls.exceptions.IngredientNotFoundInInventoryException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
public class Inventory {
    private Map<String, Ingredient> ingredients;
    private Map<String, Beverage> beverages;

    /**
     * This method returns the ingredient object to the coffee machine if present
     * @param ingredientName
     * @return
     * @throws IngredientNotFoundInInventoryException
     */
    public Ingredient getIngredient(String ingredientName) throws IngredientNotFoundInInventoryException {
        if(!ingredients.containsKey(ingredientName)){
            throw new IngredientNotFoundInInventoryException("Ingredient not found in Inventory.");
        }
        return ingredients.get(ingredientName);
    }

    /**
     * This method returns the list of all the ingredients which are running low
     * @return
     */
    public List<String> getIngredientsRunningLow() {
        List<String> ingredientsRunningLow = new ArrayList<String>();
        for(Map.Entry<String, Ingredient> entry : ingredients.entrySet()){
            if(entry.getValue().getIngredientIndicatorValue() == IngredientIndicatorValue.LOW){
                ingredientsRunningLow.add(entry.getKey());
            }
        }
        return ingredientsRunningLow;
    }

    /**
     * This method returns the requested beverage object by the coffee machine
     * @param beverageName
     * @return
     * @throws BeverageNotFoundInInventoryException
     */
    public Beverage getBeverage(String beverageName) throws BeverageNotFoundInInventoryException {
        if(!beverages.containsKey(beverageName)){
            throw new BeverageNotFoundInInventoryException("Beverage not served by the Machine currently.");
        }
        return beverages.get(beverageName);
    }

    /**
     * This method refills all the ingredients in the inventory
     */
    public void refillAll() {
        for(Map.Entry<String, Ingredient> entry : ingredients.entrySet()){
            entry.getValue().refill();
        }
    }
}
