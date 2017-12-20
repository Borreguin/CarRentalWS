package dataHandlers;

import classes.Car;
import classes.Client;
import settings.App_config;
import settings.Business_conf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/***
 *   Author: Roberto Sanchez A. 
 *   Date:   12/19/17
 ***/



public class PostgreSQLHandler {

    private Connection c = null;

    // Car table settings:
    private static final String TableNameCar = "car_tb";
    private static final String ModelID = "model";
    private static final String TypeID = "type";
    private static final String ID_car = "id";

    // Client table settings:
    private static final String TablenameCl = "client_tb";
    private static final String ClientAge = "age";
    private static final String Membership = "membership";
    private static final String ID_client = "id_client";


    /**
     * This manage the connection with the PostgreSQL server
     * This DB keeps the Business model to deal with.
     */
    public PostgreSQLHandler() {
        try {
            Class.forName("org.postgresql.Driver");

            Class.forName("org.postgresql.Driver");

            c = DriverManager
                    .getConnection("jdbc:postgresql://" + App_config.BusinessModelDB_IP +
                                    ":" + App_config.BusinessModelDB_Port    +
                                    "/" + App_config.BusinessModelDB_Name ,
                                    App_config.DB_User, "test");
            create_car_table();
            create_client_table();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }

        System.out.println("Opened business model database successfully");
    }



    /**
     * Creates a table for managing information about the available cars
     * @return True if it was successfully created, false otherwise.
     */
    public boolean create_car_table(){


        // TODO: Car classification could be done by another relational table
        // -- For simplicity and assuming that we are not going to change
        // the classification of the cars:
        String TypeCar = Business_conf.car_classification;
        // --------------------------------------------------------------

        PreparedStatement ps;
        try {

            String sql_carTable = "CREATE TABLE IF NOT EXISTS " + TableNameCar +
                    "("
                        + ID_car  +  " INTEGER PRIMARY KEY, "
                        + ModelID +  " VARCHAR (50) UNIQUE NOT NULL, "
                        + TypeID  +  " VARCHAR (50) CHECK (" + TypeID + " in (" + TypeCar +"))"
                    + ")";
            ps = c.prepareCall(sql_carTable);
            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Creates a table for managing information about the clients
     * @return True if it was successfully created, false otherwise.
     */
    public boolean create_client_table(){

        // TODO: This implementation could add additional data for the client
        // -- For simplicity not additional data as e-mail, name, and others
        // are considered. (according the problem statement)
        // --------------------------------------------------------------

        PreparedStatement ps;
        try {

            String sql_statement = "CREATE TABLE IF NOT EXISTS " + TablenameCl +
                    "("
                    + ID_client  +  " INTEGER PRIMARY KEY, "
                    + ClientAge +   " INTEGER NOT NULL, "
                    + Membership  + " BOOLEAN NOT NULL"
                    + ")";
            ps = c.prepareCall(sql_statement);
            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    /***
     * Insert a car in the Business Model
     * @return True if it was successfully inserted, false otherwise
     */
    public boolean insert_car(Car car){

        PreparedStatement ps;
        try {
            String sql_insert_car = "INSERT INTO " + TableNameCar +
                    "(" + ID_car  + ", " + ModelID + ", " + TypeID + ") " +
                    "VALUES (" + car.getId() + ", '" + car.getModel() + "', '" +car.getType()+ "')";

            ps = c.prepareCall(sql_insert_car);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /***
     * Insert a car in the Business Model
     * @return True if it was successfully inserted, false otherwise
     */
    public boolean insert_client(Client client){
        PreparedStatement ps;
        try {
            String sql_insert_cl = "INSERT INTO " + TablenameCl +
                    "(" + ID_client  + ", " + ClientAge + ", " + Membership + ") " +
                    "VALUES (" + client.getId_client() + ", '" + client.getAge() + "', '" +
                    client.getMembership() + "')";

            ps = c.prepareCall(sql_insert_cl);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


}
