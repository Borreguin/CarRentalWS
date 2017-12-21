package settings;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/***
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 ***/
public class App_config {
    /***
     * Contains global variables and settings about this application
     * this assists the installation of the CarRentalWS application in other environments.
     */


    public final static String BusinessModelDB_IP = "localhost";
    public final static String BusinessModelDB_Port = "5432";
    public final static String BusinessModelDB_Name = "businessmodel";
    public final static String DB_User = "businessmodel";

    public final static String BusinessTransactionsDB_IP = "localhost";
    public final static String BusinessTransactionsDB_Port = "9090";

    // Time configuration:
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSS'Z'");
    // Using a Gregorian calendar:
    public final static int idMonday = 2;

    // Messages:
    public static final String SUCCESS_RESULT = "Successful operation";
    public static final String FAILURE_RESULT = "Fail";

    // path project
    private String pathProject;

    public App_config() {

        // Getting the root directory:
        String suffix = "WEB-INF";
        String path[] = App_config.class.getResource("App_config.class").toString().split(suffix);
        pathProject = path[0].replace("file:/", "") + suffix;

    }

    public String root() {

        return "/" + this.pathProject; //for linux
        //return this.pathProject; //for Windows

    }
}
