package settings;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

/**
 * Contains variables and settings for the business logic
 */
public class Business_conf {

    // age to apply recharges for people younger than @young_age
    public static final int young_age = 25;
    // client is at least @limit_age years old
    public static final int limit_age = 18;
    // a x% increase for younger people
    public static final float recharge = 0.25f;

    // TODO: classification of cars could be in a database
    // For simplicity and assuming that we are not going to change
    // the classification of the cars and prices:
    private Map<String, Float> carPrice = new HashMap<>();

    // TODO: list of discounts could be in a relational database
    // For simplicity, let's put the list of discounts in a HashMap variable
    private static Map<String, Float> discountValues = new HashMap<>();

    // TODO: list of insurance polices could be in a relational database
    // For simplicity, let's put the list of polices in a HashMap variable
    private static Map<String, Float> policyValues = new HashMap<>();

    /***
     * This constructor allows the initialization of global variables
     */

    public Business_conf(){

        // car prices per day
        carPrice.put("small", 40.f);
        carPrice.put("sport", 60.f);
        carPrice.put("SUV", 100.f);

        // discounts
        discountValues.put("onWeekday", 0.10f);
        discountValues.put("3 to 5", 0.05f);
        discountValues.put("6 to 10", 0.10f);
        discountValues.put("> 11", 0.15f);
        discountValues.put("membership", 0.05f);

        // polices per day
        policyValues.put("small", 5.f);
        policyValues.put("sport", 7.f);
        policyValues.put("SUV", 10.f);
    }


    public float getCarPrice(String typeCar) {
        float rs = 0.f;
        try{
            rs = carPrice.get(typeCar);
        }
        catch (Exception e){
            System.out.print("Invalid type of the car: " + typeCar);
            e.printStackTrace();
        }
        return rs;
    }

    public static Map<String, Float> getDiscountValues() {
        return discountValues;
    }

    public static Map<String, Float> getPolicyValues() {
        return policyValues;
    }

    /**
     * auxiliary function
     * @return the classification of cars
     */
    public String getCar_classification(){

        Set rsK  = carPrice.keySet();
        StringBuilder rs = new StringBuilder("#");
        for(Object ob : rsK){
            rs.append(",'").append(ob).append("'");
        }
        rs = new StringBuilder(rs.toString().replace("#,", ""));
        return rs.toString();
    }
}
