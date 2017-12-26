package business_logic;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/20/17
 */


import classes.Car;
import classes.Client;
import org.json.JSONArray;
import org.json.JSONObject;
import settings.Business_conf;

import java.text.ParseException;
import java.util.*;

/**
 * Core.class implements the core operation of the business and some auxiliary functions:
 * Classify by type of car (small, sport, SUV) and set a price depending on the type of car
 * (40USD/day for small cars, 60USD/day for sport cars, 100USD/day for SUV cars).
 *  - Allow for a discount on weekdays (10%).
 *  - Allow for a discount for number of rental days (3 or more).
 *      For 3 to 5 days 5%.
 *      For 6 to 10 days 10%
 *      11 or more 15%
 * Differentiate price for people subscribed to a membership plan (5%).
 * Generate an insurance policy and differentiate its price for people younger than 25 years old.
 * (5USD a day for the small car, 7USD a day for the sport car, 10USD a day for the SUV
 * with a 25% increase for younger people). No discount applies over the insurance total.
 * Make sure that the person renting the car is at least 18 years old.
 *
 */


public class Core {
    // main variables:
    private float total;
    private float discount_total;
    private float insurance_total;

    // To manage the json object:
    private static final String KeyCar = "car";
    private static final String KeyRentDates = "rentDates";
    private static final String KeyMembership = "membership";
    private static final String KeyAge = "age";
    private static final String KeyModel = "model";
    private static final String KeyType = "type";

    private static final String KeySubtotal = "subtotal";
    private static final String KeyInsurance = "insuranceTotal";
    private static final String KeyDiscount = "discountPercentage";
    private static final String KeyTotalPayment = "totalPayment";

    // discount is a set of rules that are implemented over /business_logic/Discounts
    private Discounts toDiscountIf = new Discounts();

    // insurance is a set of rules implemented over /business_logic/InsurancePolicy
    private InsurancePolicy insurancePolicy = new InsurancePolicy();


    /**
     * This is the core function of the business, it calculates how much a client
     * should pay according to the inputJson object.
     * @param inputJson it has the format "{"rentDates":["2017-11-19T05:00:00.000Z",...],
     *                  "car":{"model":"Cherato","type":"sport"},"membership":false,"age":24}"
     * @return  if the inputJson is correct (lazy check) then it calculates the total value to pay,
     *          otherwise a json object: {"transaction":"unsuccessful"} is returned.
     */
    public JSONObject getTotalOfRent(JSONObject inputJson)  {

        JSONObject outputJson = new JSONObject();
        // check if @inputJson has the correct format.
        if(!checkJSONObject(inputJson)){
            outputJson.put("details","Incorrect Json format");
            return outputJson.put("transaction","unsuccessful");
        }

        // Lets assume that inputJson is a valid Json input because @checkJSONObject
        // makes a rigorous check then the following calculates the total value to pay:

        // Make instances of @Car.class and @Client.class from the inputJson:
        JSONObject auxCar =  (JSONObject) inputJson.get(KeyCar);
        Car rentedCar = new Car(
                (String) auxCar.get(KeyModel),
                (String) auxCar.get(KeyType)
        );
        Client renterClient = new Client(
                inputJson.getBoolean(KeyMembership),
                inputJson.getInt(KeyAge)
        );

        // Before any further calculation, check if the client is at least @Business_conf.limit_age years old
        if(renterClient.getAge() < Business_conf.limit_age){
            System.out.println("Client age check. Client is " + renterClient.getAge() + " years old" +
                    " ( < " + Business_conf.limit_age + ")");
            outputJson.put("details","Client is not at least " + Business_conf.limit_age + " years old");
            return outputJson.put("transaction","unsuccessful");
        }

        // List of the rented dates:
        List<String> rentDates = getListFromJsonArray(inputJson.getJSONArray(KeyRentDates));

        // To pay according to the type of car and number of days:
        float subtotal = getPayValuePerDays(rentDates, rentedCar.getPricePerDay());

        // Apply discounts according to the problem
        try {
            discount_total = toDiscountIf.onWeekdays(rentDates);
            discount_total += toDiscountIf.byNumberOfDays(rentDates);
            discount_total += toDiscountIf.byMembership(renterClient.getMembership());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Apply insurance policy
        insurance_total = insurancePolicy.apply(rentDates.size(),renterClient.getAge(), rentedCar.getType());

        // Calculate the total value to pay
        total = subtotal - subtotal*discount_total  + insurance_total;

        // Creating the outputJson result:
        outputJson.put(KeySubtotal,subtotal);
        outputJson.put(KeyInsurance,insurance_total);
        outputJson.put(KeyDiscount,discount_total*100);
        outputJson.put(KeyTotalPayment,total);
            // To remark that the process was successfully done
        outputJson.put("transaction","successful");

        return outputJson;
    }

    /**
     * Let's assume that @checkJSONObject makes a rigorous check of the list of dates:
     *  -correct format
     *  -not repeated dates
     *  -valid date
     * therefore this function calculates the subtotal according the number of days
     * @return calculates the subtotal according the number of days
     */
    private float getPayValuePerDays(List<String> dates, float price){
        return dates.size()*price;
    }


    // TODO: More rigours checking, it was done in this way only for simplicity
    /**
     * Lazy check of the Json object, check whether or not the json object contains the
     * expected keys.
     * @param jsonObj this object contains the keys: "rentDates", "car", "membership", "age"
     * @return true if the object meets the expected keys, false otherwise.
     */
    private Boolean checkJSONObject(JSONObject jsonObj){
        Set keys = jsonObj.keySet();
        Set<String> toCheck = new HashSet<>(Arrays.asList(KeyAge, KeyCar, KeyRentDates, KeyMembership));
        return keys.equals(toCheck);
    }

    /**
     * Auxiliary function: Obtain a List of Strings from a JSONArray
     * @param arr is a JSONArray
     * @return List of Strings
     */
    private List<String> getListFromJsonArray(JSONArray arr){

        List<String> list = new ArrayList<>();
        for(Object element : arr){
            list.add((String) element);
        }
        return list;
    }


}
