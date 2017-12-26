package wbservices;

import business_logic.Core;
import classes.Car;
import classes.Transaction;
import dataHandlers.PostgreSQLHandler;
import dataHandlers.TransactionHandler;
import org.json.JSONObject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;
import static settings.App_config.FAILURE_RESULT;
import static settings.App_config.SUCCESS_RESULT;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/

/**
 * @ResourcesServices implements the following services:
 *
 * Car Service
 * ------------------------------------------------------
 * GET: (shows all the available cars)
 *      http://localhost:8080/WebService/resources/cars
 *
 * GET: (shows a car of model {carModel} )
 *      http://localhost:8080/WebService/resources/cars/{carModel}
 *
 * POST: (Add a car, a form is needed to pass @model and @type of the car)
 *      http://localhost:8080/WebService/resources/cars
 *
 * DELETE: (deletes a car of model {carModel} )
 *      http://localhost:8080/WebService/resources/cars/{carModel}*
 *
 * Transaction Service
 * ------------------------------------------------------
 * GET: (shows all the transactions)
 *      http://localhost:8080/WebService/resources/rents
 *
 * POST: (creates a transaction according to the JSON input String {inputJsonStr}
 *      the resulting transaction is saved in the web server)
 *      http://localhost:8080/WebService/resources/rents/{inputJsonStr}
 *
 * The logic of the Business is implemented over @{@link business_logic.Core}
 */

@Path("/resources")
public class ResourcesService {

    private PostgreSQLHandler post = new PostgreSQLHandler();
    private TransactionHandler trans = new TransactionHandler();
    private Core businessCore = new Core();


    // Gets all cars: XML Format
    @GET
    @Path("/cars")
    @Produces(MediaType.APPLICATION_XML)
    public List<Car> getCarsXML(){
        return post.getAllCars();
    }


    // Gets all cars: JSON Format
    @GET
    @Path("/jcars")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars(){
        return post.getAllCars();
    }


    // Gets car by idModel
    @GET
    @Path("/cars/{carModel}")
    @Produces(MediaType.APPLICATION_XML)
    public Car getCar(@PathParam("carModel") String modelID){
        return post.getCar(modelID);
    }

    // creating a new car
    @POST
    @Path("/cars")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String createCar(
                             @FormParam("model") String modelID,
                             @FormParam("type") String typeID,
                             @Context HttpServletResponse serveltReponse)
            throws IOException {
        Car car = new Car( modelID, typeID);
        Boolean result = post.insert_car(car);
        if(result){
            return SUCCESS_RESULT;
        }
        return FAILURE_RESULT;
    }


    // Delete a car
    @DELETE
    @Path("/cars/{carModel}")
    @Produces(MediaType.APPLICATION_XML)
    public String deleteCar(@PathParam("carModel") String modelID){
        boolean result = post.delete_car(modelID);
        if(result)
            return SUCCESS_RESULT;
        return FAILURE_RESULT;
    }

    // Options:
    @OPTIONS
    @Path("/cars")
    @Produces(MediaType.APPLICATION_XML)
    public String getSupportedOperations(){

        return "<operations>GET, PUT, POST, DELETE</operations>";
    }


    // Renting a car
    @POST
    @Path("/rents")
    @Produces(MediaType.APPLICATION_XML)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String rentCar(
            @FormParam("inputJsonSt") String inputJsonSt,
            @Context HttpServletResponse serveltReponse)
            throws IOException {
        String rs = FAILURE_RESULT;
        JSONObject inputJSON = new JSONObject(inputJsonSt);
        JSONObject outputJSON = businessCore.getTotalOfRent(inputJSON);

        if(outputJSON.getString("transaction").equals("successful"))
            rs = SUCCESS_RESULT;

        List<Transaction> transactionList = trans.getAllTransactions();
        int lSize = 0;
        if(transactionList != null) lSize = transactionList.size();
        trans.addTransaction(new Transaction(lSize+1, inputJSON, outputJSON));
        return rs;
    }

    // Gets all transactions:
    @GET
    @Path("/rents")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Transaction> getRents(){
        return trans.getAllTransactions();
    }

}
