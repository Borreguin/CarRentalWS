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

public class MainApp extends Application{
    /***
     * It implements the main application where we can add the needed services
     * For now only:
     *   -
     *   -
     *
     * @return running application
     */



    @Override
    public Set<Class<?>> getClasses(){
        /***
         *  HashSet h contains the included services for this Application
         */
        HashSet h = new HashSet<Class<?>>();
        // Here include the needed services
        /*h.add(MainPage.class);
        h.add(UserService.class);
        h.add(imageService.class);
        h.add(BookService.class);*/
        PostgreSQLHandler post = new PostgreSQLHandler();
        post.create_car_table();

        return h;
    }

}
