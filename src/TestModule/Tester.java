package TestModule;


import classes.Car;
import dataHandlers.PostgreSQLHandler;
import org.glassfish.jersey.client.ClientConfig;

import javax.ws.rs.client.*;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static settings.App_config.SUCCESS_RESULT;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/
public class Tester {


    // for this test is necessary this dependence:
    // org.glassfish.jersey.core:jersey-client:2.10
    // needs guava 2.24 or superior.
    private Client webClient;
    private String REST_SERVICE_URL = "http://localhost:8080/WebService/CarService/cars";
    private static final String PASS = "pass";
    private static final String FAIL = "fail";
    private static final String modeID_toTest = "Test Model";


    private void init() {
        this.webClient = ClientBuilder.newClient();
    }

    public static void main(String[] args) {
        Tester tester = new Tester();
        //initialize the tester
        tester.init();
        //test all cars Web Service Method
        //test get all cars Web Service Method
        tester.testGetAllCars();
        //test get car Web Service Method
        tester.testGetCar();
        //test add car Web Service Method
        tester.testAddCar(modeID_toTest);
        //test delete car Web Service Method
        tester.testDeleteCar(modeID_toTest);


    }

    //Tester: Get list of all cars
    //Tester: Check if list is not empty
    private void testGetAllCars() {
        GenericType<List<Car>> list = new GenericType<List<Car>>() {};
        String result = FAIL;
        try {
            List<Car> cars = webClient
                    .target(REST_SERVICE_URL + "_xml")
                    .request(MediaType.APPLICATION_XML)
                    .get(list);
            result = PASS;
            if (cars.isEmpty()) {
                result = FAIL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Tester case name: testGetAllCars, Result: " + result);


    }


    //Tester: Get Car of id 1
    //Tester: Check if car is same as sample car
    private void testGetCar() {
        Car SampleCar = new Car("","");
        SampleCar.setId(1);
        String result = FAIL;

        try {
            Car car = webClient
                    .target(REST_SERVICE_URL)
                    .path("/{carModel}")
                    .resolveTemplate("carModel", modeID_toTest)
                    .request(MediaType.APPLICATION_XML)
                    .get(Car.class);

            if (SampleCar.getId() == car.getId()) {
                result = PASS;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        System.out.println("Tester case name: testGetCar, Result: " + result);
    }


    //Test: Add a car model: Model Test
    //Test: Check if result is a successfully XML.
    private void testAddCar(String modeID_toTest){
        Form form = new Form();
        form.param(PostgreSQLHandler.ModelID, modeID_toTest);
        form.param(PostgreSQLHandler.TypeID, "sport");

        String callResult = webClient
                .target(REST_SERVICE_URL)
                .request(MediaType.APPLICATION_XML)
                .post(Entity.entity(form,
                        MediaType.APPLICATION_FORM_URLENCODED_TYPE),
                        String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("Test case name: testAddCar, Result: " + result );
    }

    //Test: Delete a car model: Model Test
    //Test: Check if result is a successfully XML.
    private void testDeleteCar(String modelID){
        String callResult = webClient
                .target(REST_SERVICE_URL)
                .path("/{carModel}")
                .resolveTemplate("carModel", modelID)
                .request(MediaType.APPLICATION_XML)
                .delete(String.class);

        String result = PASS;
        if(!SUCCESS_RESULT.equals(callResult)){
            result = FAIL;
        }

        System.out.println("Test case name: testDeleteCar, Result: " + result );
    }



}
