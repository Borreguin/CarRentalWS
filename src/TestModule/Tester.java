package TestModule;


import classes.Car;
import dataHandlers.PostgreSQLHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import static settings.App_config.SUCCESS_RESULT;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/

/**
 * @Tester.class tests:
 *      1. Web services for CRUD actions of cars
 *      2. Transactions for renting of cars coming from @jsonFile
 *
 *  Details about the web service in @{@link wbservices.ResourcesService}
 *  The logic of the Business is implemented over @{@link business_logic.Core}
 */

public class Tester {


    // For this test is necessary this dependence:
    // org.glassfish.jersey.core:jersey-client:2.10 (included as a Maven project)

    private Client webClient;
    // The rest service URL to test:
    private String REST_SERVICE_URL = "http://localhost:8080/WebService/resources";
    private static final String PASS = "pass";
    private static final String FAIL = "fail";
    // A testing car for CRUD actions:
    private static final Car testingCar = new Car("Test Model","small");

    // A JSON input file to test:
    private static final String jsonFile = "input.json";

    private void init() {
        this.webClient = ClientBuilder.newClient();
    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        //initialize the tester
        tester.init();

        // ---- Testing the Web Service Methods -------------
        // --------------------------------------------------
        System.out.println("\nWeb services for cars" );
        //test Insert car Web Service Method:
        //test Insert car Web Service Method:
        tester.testAddCar(testingCar);
        //test get car Web Service Method
        tester.testGetCar(testingCar.getModel());
        //test Delete car Web Service Method:
        tester.testDeleteCar(testingCar.getModel());

        // Inserting initial available cars:
        tester.insertInitialCars();
        //test get all cars Web Service Method
        tester.testGetAllCars();

        // ---- End Testing the Web Service Methods ----------
        // ---------------------------------------------------

        // ---- Testing transactions for renting a car -------
        // ---------------------------------------------------
        System.out.println("\nTest case name: Transactions from the client" );
        JSONArray jsonArray = readFromJsonFile(jsonFile);
        for(Object inputJson : jsonArray){
            tester.testSendJsonString((JSONObject) inputJson);
        }

        // -End Testing transactions for renting a car -------
        // ---------------------------------------------------


    }


    /**
     * Tester: Get list of all cars in the BusinessModel
     */
    private void testGetAllCars() {
        GenericType<List<Car>> list = new GenericType<List<Car>>() {};
        String result = FAIL;
        try {
            List<Car> cars = webClient
                    .target(REST_SERVICE_URL + "/cars")
                    .request(MediaType.APPLICATION_XML)
                    .get(list);
            result = PASS;
            if (cars.isEmpty()) {
                result = FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("[" + result + "]\tTest case name: testGetAllCars");
    }


    /**
     * Tester: Get Car of id @model
     * @param model to get
     * @return true if the car exists, false otherwise
     */
    private boolean testGetCar(String model) {
        String result = FAIL;
        Boolean successfully = false;

        try {
            Car car = webClient
                    .target(REST_SERVICE_URL + "/cars")
                    .path("/{carModel}")
                    .resolveTemplate("carModel", model)
                    .request(MediaType.APPLICATION_XML)
                    .get(Car.class);

            if( car != null){
                if (car.getModel().equals(model)) {
                    result = PASS;
                    successfully = true;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("[" + result + "]\tTest case name: testGetCar(" + model + ")" );
        return successfully;
    }


    /**
     * Test: Add a car model: Model Test
     * @param car to insert in the Business DB model
     */
    private void testAddCar(Car car){
        Form form = new Form();
        form.param(PostgreSQLHandler.ModelID, car.getModel());
        form.param(PostgreSQLHandler.TypeID, car.getType());

        String callResult = webClient
                .target(REST_SERVICE_URL + "/cars")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("[" + result + "]\tTest case name: testAddCar(" + car.getModel() + ")");
    }



    /**
     * Test: Delete a car model: Model Test
     * @param modelID ID of the model
     */
    private void testDeleteCar(String modelID){
        String callResult = webClient
                .target(REST_SERVICE_URL + "/cars")
                .path("/{carModel}")
                .resolveTemplate("carModel", modelID)
                .request(MediaType.APPLICATION_XML)
                .delete(String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("[" + result + "]\tTest case name: testDeleteCar(" + modelID + ")" );
    }

    /**
     * Adding cars to the Business DB model:
     */
    private void insertInitialCars(){
        System.out.println("\nTest case name: insertInitialCars" );
        List<Car> cars = new ArrayList<>();
        cars.add(new Car("Dwafty", "small"));
        cars.add(new Car("Halfing", "small"));
        cars.add(new Car("Eveo","sport"));
        cars.add(new Car("Cherato", "sport"));
        cars.add(new Car("Vitoro", "SUV"));
        cars.add(new Car("Exploring", "SUV"));

        for(Car car : cars){
            if(!testGetCar(car.getModel())){
                testAddCar(car);
            }
        }

    }

    /**
     * Rent a car according to the input jsonObject.
     * It prints: success if the action was correct, fail otherwise.
     * @param jsonObject follows the following syntax:
     *                   "{"rentDates":["2017-11-19T05:00:00.000Z",...],
     *                   "car":{"model":"Cherato","type":"sport"},"membership":false,"age":24}"
     */
    private void testSendJsonString(JSONObject jsonObject){
        String inputJsonSt = jsonObject.toJSONString();

        Form form = new Form();
        form.param("inputJsonSt", inputJsonSt);

        String callResult = webClient
                .target(REST_SERVICE_URL + "/rents")
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }
        System.out.println("[" + result + "]\tTest case name: rent client");
    }


    /**
     * Lets assume the input file is already checked (validated) and it follows the following
     * syntax:
     *        "{"rentDates":["2017-11-19T05:00:00.000Z",...],
     *        "car":{"model":"Cherato","type":"sport"},"membership":false,"age":24}"
     *
     * Note: if a input json request is wrong, the web service is going to produce an unsuccessful response
     * @param inputFile a JSON file to test
     * @return return a JSON array from the testing input file
     */
    private static JSONArray readFromJsonFile(String inputFile){

        JSONParser parser = new JSONParser();
        JSONArray rs = null;
        try {

            String filePath = "/" + testPath() + inputFile; // For linux
            // String filePath = testPath() + inputFile;    // For Windows

            Object obj = parser.parse(new FileReader(filePath));
            rs = (JSONArray) obj;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


    /**
     * This function may change depending on the OS
     * For now, it is setup for Linux OS environment
     * @return The path for the testing Module
     */
    private static String testPath(){
        // Getting the Test directory:

        String suffix = "Tester.class";
        String path[] = Tester.class.getResource(suffix).toString().split(suffix);
        return path[0].replace("file:/", "") ;
    }
}
