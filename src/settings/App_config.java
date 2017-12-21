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
}
