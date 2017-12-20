import classes.Car;
import classes.Client;
import dataHandlers.PostgreSQLHandler;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/***
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 ***/

@ApplicationPath("/")
/**
 * * It implements the main application where we can add the needed services
 * For now only:
 *   -
 *   -
 *
 * @return running application
 */
public class MainApp extends Application{



    /**
     * HashSet h contains the included services for this Application
     * @return a collection of classes of services.
     */
    @Override
    public Set<Class<?>> getClasses(){

        HashSet h = new HashSet<Class<?>>();
        // Here include the needed services
        /*h.add(MainPage.class);
        h.add(UserService.class);
        h.add(imageService.class);
        h.add(BookService.class);*/
        PostgreSQLHandler post = new PostgreSQLHandler();
        //post.create_car_table();
        //post.create_client_table();
        post.insert_car(new Car("Dwarfy", "small"));
        post.insert_client(new Client(true, 24));
        Car car = post.getCar("Dwarfy");
        post.delete_car(car);

        return h;
    }

}
