package dataHandlers;

import settings.App_config;

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

        String TableName = "car_tb";
        String ModelID = "model";
        String TypeID = "type";

        PreparedStatement ps;
        try {

            String sql_carTable = "CREATE TABLE " + TableName + "("
                    + ModelID +  " INTEGER PRIMARY KEY,"
                    + TypeID  +  " TEXT," + ")";
            ps = c.prepareCall(sql_carTable);
            ps.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }


}
