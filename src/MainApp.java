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
 *   - Car service
 *   - Transaction service
 *
 * Others can be added like: user service, etc.
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
        // Here include the needed services
        h.add(ResourcesService.class);
        return h;
    }

}
