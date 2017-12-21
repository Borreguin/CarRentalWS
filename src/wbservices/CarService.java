package wbservices;

import classes.Car;
import dataHandlers.PostgreSQLHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
*   Author: Roberto Sanchez A. 
*   Date:   12/21/17
*/

@Path("/CarService")
public class CarService {

    private PostgreSQLHandler post = new PostgreSQLHandler();


    // Gets all cars:
    @GET
    @Path("/cars")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Car> getCars(){
        List<Car> cars = post.getAllCars();
        return cars;
    }

    /*
    // Gets car by idModel
    @GET
    @Path("/cars/{carModel}")
    @Produces(MediaType.APPLICATION_JSON)
    public Car getCar(@PathParam("carModel") String modelID){
        return post.getCar(modelID);
    }

    // creating a new car
    @POST
    @Path("/cars")
    @Produces(MediaType.APPLICATION_JSON)
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
    }*/

    // Options:
   /* @OPTIONS
    @Path("/cars")
    @Produces(MediaType.APPLICATION_JSON)
    public String getSupportedOperations(){

        return "<operations>GET, PUT, POST, DELETE</operations>";
    }*/
}
