package business_logic;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/20/17
 */


import classes.Car;
import classes.Client;
import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.*;

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
     *          otherwise an empty json object.
     */
    public JSONObject getTotalOfRent(JSONObject inputJson)  {
        JSONObject outputJson = new JSONObject();
        if(!checkJSONObject(inputJson)){
            return outputJson;
        }
        // Let's assume that inputJson is a valid Json input because @checkJSONObject
        // makes a rigorous check

        JSONObject auxCar =  (JSONObject) inputJson.get(KeyCar);
        Car rentedCar = new Car(
                (String) auxCar.get(KeyModel),
                (String) auxCar.get(KeyType)
        );
        Client renterClient = new Client(
                inputJson.getBoolean(KeyMembership),
                inputJson.getInt(KeyAge)
        );

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
        insurance_total = insurancePolicy.apply(rentDates.size(),renterClient.getAge(),rentedCar.getType());

        total = subtotal - subtotal*discount_total  + insurance_total;

        outputJson.put(KeySubtotal,subtotal);
        outputJson.put(KeyInsurance,insurance_total);
        outputJson.put(KeyDiscount,discount_total*100);
        outputJson.put(KeyTotalPayment,total);


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
   public float getPayValuePerDays(List<String> dates, float price){
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
        if(keys.equals(toCheck)){
            return true;
        }
        return false;

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
