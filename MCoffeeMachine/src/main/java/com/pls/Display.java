package com.pls;

import com.pls.models.Beverage;
import lombok.NonNull;

import java.util.List;

public class Display {

    public static void beverageIngredientsQuantityNotAvailable(String beverageName){
        System.out.println("Ingredients of requested beverage - " +
                            beverageName +
                            " are not sufficient in the Inventory.");
    }

    public static void beverageServed(String beverageName) {
        System.out.println("Requested beverage - " +
                            beverageName +
                            " prepared and served.");
    }

    public static void freeOutletNotAvailable(String beverageName) {
        System.out.println("Requested beverage - " +
                beverageName +
                " could not be served right now as all the outlets are busy.");
    }

    public static void noIngredientIsRunningLow() {
        System.out.println("None of the ingredients in Inventory is running low.");
    }

    public static void ingredientsRunningLow(List<String> ingredientsRunningLow) {
        System.out.println("List of Ingredients running low: [" +
                            String.join(", ", ingredientsRunningLow) +
                            "]");
    }

    public static void ingredientAlreadyFull(String ingredientName) {
        System.out.println("Ingredient " +
                            ingredientName +
                            " already full.");
    }

    public static void allIngredientsRefilled() {
        System.out.println("All ingredients refilled.");
    }

    public static void ingredientsRefilled(String ingredientName) {
        System.out.println("Ingredients - " +
                            ingredientName +
                            " refilled.");
    }

    public static void showTestCaseMessage(int lastTestcase) {
        System.out.println("Enter the test case name, format: test_case_x (1 <= x <= " +
                           lastTestcase +
                           ")");
    }
}
