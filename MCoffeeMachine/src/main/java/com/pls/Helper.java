package com.pls;

import com.pls.enums.IngredientIndicatorValue;

public class Helper {
    private static final Double LEVEL_TWO_MARK = 0.75;
    private static final Double LEVEL_ONE_MARK = 0.25;

    public static IngredientIndicatorValue calculateIngredientIndicatorValue(final Double ingredientMaximumQuantity,
                                                                             final Double ingredientCurrentQuantity){

        if(ingredientCurrentQuantity >= LEVEL_TWO_MARK * ingredientMaximumQuantity){
            return IngredientIndicatorValue.FULL;
        }

        if(ingredientCurrentQuantity >= LEVEL_ONE_MARK * ingredientMaximumQuantity){
            return IngredientIndicatorValue.HALF;
        }

        return IngredientIndicatorValue.LOW;
    }
}
