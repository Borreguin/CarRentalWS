package settings;

import java.text.SimpleDateFormat;
import java.util.logging.SimpleFormatter;

/*
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 */

/**
 * Contains global variables and settings about this application
 * this assists the installation of the CarRentalWS application in other environments.
 */

public class App_config {

    // Setup for the DB Server that contains the business model information
    public final static String BusinessModelDB_IP = "localhost";
    public final static String BusinessModelDB_Port = "5432";
    public final static String BusinessModelDB_Name = "businessmodel";
    public final static String DB_User = "businessmodel";

    //TODO: To store transactions produced for the client: (MongoDB server could be a good solution)
    public final static String BusinessTransactionsDB_IP = "localhost";
    public final static String BusinessTransactionsDB_Port = "9090";

    // Time configuration:
    public SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd'T'hh:mm:ss.SSS'Z'");
    // Using a Gregorian calendar:
    public final static int idMonday = 2;

    // Notification Messages for the implemented web service:
    public static final String SUCCESS_RESULT = "<result>success</result>";
    public static final String FAILURE_RESULT = "<result>failure</result>";

    // path of the project
    private String pathProject;

    public App_config() {

        // Getting the root directory:
        // The following may change depending on the OS.
        String suffix = "WEB-INF";
        String path[] = App_config.class.getResource("App_config.class").toString().split(suffix);
        pathProject = path[0].replace("file:/", "") +  suffix;

    }

    /**
     * Gets the directory path of the project:
     * @return directory path of the project
     */
    public String root() {

        return "/" + this.pathProject; //for linux
        //return this.pathProject; //for Windows

    }
}
