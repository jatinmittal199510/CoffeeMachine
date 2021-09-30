package com.pls;

import com.pls.commands.Command;
import com.pls.exceptions.BeverageNotFoundInInventoryException;
import com.pls.exceptions.IngredientNotFoundInInventoryException;
import com.pls.models.Beverage;
import com.pls.models.CoffeeMachine;
import com.pls.models.Ingredient;
import com.pls.models.Inventory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static Map<String, Ingredient> ingredients;
    private static Map<String, Beverage> beverages;
    private static int totalOutlets;
    private static final int LAST_TEST_CASE = 8;

    /**
     * Main method first configures the Coffee Machine with the input config.json file.
     * Then, run the test cases given in the testcases.json file as specified by the user.
     * @param args
     */
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();
        try{
            String configFile = "./src/main/java/com/pls/config.json";

            Object configFileObject = parser.parse(new FileReader(configFile));
            JSONObject configJsonObject = (JSONObject) configFileObject;

            totalOutlets = ((Number) configJsonObject.get("outlets")).intValue();

            JSONObject  ingredientsObject = (JSONObject)configJsonObject.get("total_items_quantity");
            ingredients = getIngredientsMap(ingredientsObject);

            JSONObject  beveragesObject = (JSONObject)configJsonObject.get("beverages");
            beverages = getBeveragesMap(beveragesObject);


            String testcaseFile = "./src/main/java/com/pls/testcases.json";
            Object testcaseFileObject = parser.parse(new FileReader(testcaseFile));
            JSONObject testcaseJsonObject = (JSONObject) testcaseFileObject;

            Display.showTestCaseMessage(LAST_TEST_CASE);
            String testcaseName = (new Scanner(System.in)).next();
            JSONArray commands = (JSONArray) testcaseJsonObject.get(testcaseName);

            CoffeeMachine coffeeMachine = new CoffeeMachine(new AtomicInteger(totalOutlets),
                                                            new Inventory(ingredients, beverages));

            for(int i=0; i < commands.size();i++){
                new Command(commands.get(i).toString()).execute(coffeeMachine);
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        catch (BeverageNotFoundInInventoryException e) {
            e.printStackTrace();
        }
        catch (IngredientNotFoundInInventoryException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Beverage> getBeveragesMap(JSONObject beveragesObject) {
        Map<String, Beverage> map = new HashMap<>();
        for(Object k : beveragesObject.keySet()){
            String key = k.toString();
            JSONObject  beverageObject = (JSONObject)beveragesObject.get(key);
            map.put(key, new Beverage(key, getBeverageMap(beverageObject)));
        }
        return map;
    }

    private static Map<String, Double> getBeverageMap(JSONObject beverageObject) {
        Map<String, Double> map = new HashMap<>();
        for(Object k : beverageObject.keySet()){
            String key = k.toString();
            Double val = ((Number)beverageObject.get(key)).doubleValue();
            map.put(key, val);
        }
        return map;
    }

    private static Map<String, Ingredient> getIngredientsMap(JSONObject ingredientsObject){
        Map<String, Ingredient> map = new HashMap<>();
        for(Object k : ingredientsObject.keySet()){
            String key = k.toString();
            Double val = ((Number)ingredientsObject.get(key)).doubleValue();
            map.put(key, new Ingredient(key, val));
        }
        return map;
    }

}
