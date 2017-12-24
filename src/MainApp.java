import business_logic.Core;
import classes.Car;
import dataHandlers.PostgreSQLHandler;
import wbservices.ResourcesService;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

@ApplicationPath("/")
/** It implements the main application where we can add the needed services
 * For now only:
 *   -
 *   -
 *
 * @return running application
 */
public class MainApp extends Application{

    private Core core = new Core();

    /**
     * HashSet h contains the included services for this Application
     * @return a collection of classes of services.
     */
    @Override
    public Set<Class<?>> getClasses(){

        HashSet h = new HashSet<Class<?>>();
        h.add(MainPage.class);
        h.add(ResourcesService.class);

        // Here include the needed services
         h.add(MainPage.class);
        //h.add(UserService.class);
        //h.add(imageService.class);
        //h.add(BookService.class);
        PostgreSQLHandler post = new PostgreSQLHandler();
        //post.create_car_table();
        //post.create_client_table();
        post.insert_car(new Car("Eveo", "sport"));
        post.insert_car(new Car("Dwarfy3", "small"));
        //post.insert_client(new Client(true, 24));

        /*List<Car> cars = post.getAllCars();
        //List<Client> clients = post.getAllClients();

        //Car car = post.getCar("Dwarfy");
        //post.delete_car(car);

        String json = "{\"rentDates\":[\"2017-11-19T05:00:00.000Z\",\"2017-11-20T05:00:00.000Z\",\"2017-11-21T05:00:00.000Z\"],\"car\":{\"model\":\"Cherato\",\"type\":\"sport\"},\"membership\":false,\"age\":24}";
        //json = "{\"rentDates\":[\"2017-11-19T05:00:00.000Z\",\"2017-11-20T05:00:00.000Z\",\"2017-11-21T05:00:00.000Z\"],\"car\":{\"model\":\"Cherato\",\"type\":\"sport\"},\"membership\":false}";

        JSONObject jsonObj = new JSONObject(json);
        core.getTotalOfRent(jsonObj);*/


        return h;
    }

}
