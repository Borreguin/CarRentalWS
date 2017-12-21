package wbservices;

import classes.Car;
import dataHandlers.PostgreSQLHandler;

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

@Path("/CarService")
public class CarService {

    private PostgreSQLHandler post = new PostgreSQLHandler();



    // Gets all cars:
    @GET
    @Path("/cars_xml")
    @Produces(MediaType.APPLICATION_XML)
    public List<Car> getCarsXML(){
        List<Car> cars = post.getAllCars();
        return cars;
    }


    // Gets all cars:
    @GET
    @Path("/cars")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars(){
        List<Car> cars = post.getAllCars();
        return cars;
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
                             @FormParam("modelID") String modelID,
                             @FormParam("typeID") String typeID,
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
}
