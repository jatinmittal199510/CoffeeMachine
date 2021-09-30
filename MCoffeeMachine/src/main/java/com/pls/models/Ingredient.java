package com.pls.models;

import com.pls.Display;
import com.pls.Helper;
import com.pls.enums.IngredientIndicatorValue;
import lombok.Getter;
import lombok.Setter;

@Getter
public class Ingredient {
    private String ingredientName;
    @Setter private Double ingredientCurrentQuantity;
    private Double ingredientMaximumQuantity;
    @Setter private IngredientIndicatorValue ingredientIndicatorValue;

    public Ingredient(final String ingredientName, final Double ingredientMaximumQuantity){
        this.ingredientName = ingredientName;
        this.ingredientMaximumQuantity = ingredientMaximumQuantity;
        this.ingredientCurrentQuantity = ingredientMaximumQuantity;
        ingredientIndicatorValue = IngredientIndicatorValue.FULL;
    }

    /**
     * This method updates the indicator value for the current indicator.
     * This method is called whenever this ingredient is used in the preparation of any beverage
     */
    public void updateIngredientIndicatorValue(){
        IngredientIndicatorValue newIndicatorValue =
                Helper.calculateIngredientIndicatorValue(ingredientMaximumQuantity, ingredientCurrentQuantity);
        ingredientIndicatorValue = newIndicatorValue;
    }

    /**
     * This method will refill the current ingredient object
     */
    public void refill() {
        if(ingredientCurrentQuantity == ingredientMaximumQuantity){
            Display.ingredientAlreadyFull(ingredientName);
            return;
        }
        ingredientCurrentQuantity = ingredientMaximumQuantity;
        ingredientIndicatorValue = IngredientIndicatorValue.FULL;
    }
}
