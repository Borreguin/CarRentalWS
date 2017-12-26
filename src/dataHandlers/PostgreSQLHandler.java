

package dataHandlers;

import classes.Car;
import classes.Car;
import classes.Client;
import settings.App_config;
import settings.Business_conf;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
 *   Author: Roberto Sanchez A.
 *   Date:   12/19/17
 */

/**
 * This manages the connection with the PostgreSQL server
 * This DB Server keeps the Business model to deal with.
 * @PostgreSQLHandler.class tests the connection and the existence of the Business Model tables.
 * -> Note: Settings of the DB Server are in /settings/App_config.class
 * This code assumes a trustful environment therefore not security policies are applied.
 *    Ex: DB administrator password
 */


public class PostgreSQLHandler {

    private Connection c = null;
    private Business_conf business_conf = new Business_conf();

    // Car table settings:
    private static final String TableNameCar = "car_tb";
    public static final String ModelID = "model";
    public static final String TypeID = "type";
    private static final String ID_car = "id";

    // Client table settings:
    private static final String TablenameCl = "client_tb";
    private static final String ClientAge = "age";
    private static final String Membership = "membership";
    private static final String ID_client = "id_client";

    /**
     *  Creating the connection and tables (if it is needed)
     **/
    public PostgreSQLHandler() {
        try {
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
    private boolean create_car_table(){


        // TODO: Car classification could be done by another relational table
        // -- For simplicity and assuming that we are not going to change
        // the classification of the cars:
        String TypeCar = business_conf.getCar_classification();
        // --------------------------------------------------------------

        PreparedStatement ps;
        try {

            String sql_carTable = "CREATE TABLE IF NOT EXISTS " + TableNameCar +
                    "("
                        + ID_car  +  " SERIAL PRIMARY KEY, "
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
    private boolean create_client_table(){

        // TODO: This implementation could add additional data for the client
        // -- For simplicity not additional data as e-mail, name, and others
        // are considered. (according the problem statement)
        // --------------------------------------------------------------

        PreparedStatement ps;
        try {

            String sql_statement = "CREATE TABLE IF NOT EXISTS " + TablenameCl +
                    "("
                    + ID_client  +  " SERIAL PRIMARY KEY, "
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
                    "("  + ModelID + ", " + TypeID + ") " +
                    "VALUES ('"  + car.getModel() + "', '" + car.getType()+ "')";

            ps = c.prepareCall(sql_insert_car);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }


    /***
     * Insert a client in the Business Model
     * @return True if it was successfully inserted, false otherwise
     */
    public boolean insert_client(Client client){
        PreparedStatement ps;
        try {
            String sql_st = "INSERT INTO " + TablenameCl +
                    "("  + ClientAge + ", " + Membership + ") " +
                    "VALUES (" + client.getAge() + ", " +
                    client.getMembership() + ")";

            ps = c.prepareCall(sql_st);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * Delete a car from the Business Model
     * @return True if it was successfully inserted, false otherwise
     */
    public boolean delete_car(String model){
        PreparedStatement ps;
        try {
            String sql_st = "DELETE FROM " + TableNameCar +
                    " WHERE " + ModelID + " = '" + model + "'";
            ps = c.prepareCall(sql_st);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * Delete a client from the Business Model
     * @return True if it was successfully inserted, false otherwise
     */
    public boolean delete_client(Client client){
        PreparedStatement ps;
        try {
            String sql_statement = "DELETE FROM " + TablenameCl +
                    " WHERE " + ID_client + " = " + client.getId_client() ;
            ps = c.prepareCall(sql_statement);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /***
     * Find a car in the Business Model
     * @return a valid car if it was found, a empty car otherwise
     */
    public Car getCar(String modelID){
        PreparedStatement ps;
        Car car = null;
        try {
            String sql_statement = "SELECT * FROM " + TableNameCar +
                    " WHERE " + ModelID + " = '" + modelID + "'";
            ps = c.prepareCall(sql_statement);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            if(rs.next())
            {
                car = new Car(
                    rs.getInt(1),       //ID
                    rs.getString(2),    //Model
                    rs.getString(3)     //Type
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return car;
    }

    /***
     * Find a client in the Business Model
     * @return a valid client if it was found, a empty client otherwise
     */
    public Client getClient(String id){
        PreparedStatement ps;
        Client client;
        try {
            String sql_statement = "SELECT * FROM " + TablenameCl +
                    " WHERE " + ID_client + " = " + id;
            ps = c.prepareCall(sql_statement);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            rs.next();
            client = new Client(
                    rs.getInt(1),       //ID
                    rs.getBoolean(2),   //Membership
                    rs.getInt(3)        //client age
            );
            return client;
        } catch (SQLException e) {
            e.printStackTrace();
            return new Client(false,0);
        }
    }

    /***
     * Gets all cars from the Business Model
     * @return a valid list of cars if cars were found, a empty car list otherwise
     */
    public List<Car> getAllCars(){
        PreparedStatement ps;
        List<Car> cars = new ArrayList<>();
        try {
            String sql_statement = "SELECT * FROM " + TableNameCar;
            ps = c.prepareCall(sql_statement);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                cars.add(
                        new Car(
                        rs.getInt(1),       //ID
                        rs.getString(2),    //Model
                        rs.getString(3)     //Type
                    ));
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
            return cars;
        }
    }

    /***
     * Get all clients from the Business Model
     * @return a valid list of client if clients were found, a empty list of clients otherwise
     */
    public List<Client> getAllClients(){
        PreparedStatement ps;
        List<Client> clients = new ArrayList<>();
        try {
            String sql_statement = "SELECT * FROM " + TablenameCl;
            ps = c.prepareCall(sql_statement);
            ps.execute();

            ResultSet rs = ps.getResultSet();
            while(rs.next()){

                clients.add(
                        new Client(
                                rs.getInt(1), //ID
                                rs.getBoolean(3), //Membership
                                rs.getInt(2) //client age
                        )
                );
            }
            return clients;
        } catch (SQLException e) {
            e.printStackTrace();
            return clients;
        }
    }

}
