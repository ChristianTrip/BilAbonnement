package com.example.bilabonnement.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    private static String url;
    private static String username;
    private static String password;
    private static Connection connection;

    private DatabaseConnectionManager(){}


    public static Connection getConnection(){

        if(connection != null){
            return connection;
        }
        else{
                //Properties file
            try(InputStream propertiesFile = new FileInputStream("src/main/resources/application.properties")){
                Properties properties = new Properties();
                properties.load(propertiesFile);
                url = properties.getProperty("db.url");
                username = properties.getProperty("db.username");
                password = properties.getProperty("db.password");

                connection = DriverManager.getConnection(url, username, password);
            }
            catch(SQLException | IOException e){
                e.printStackTrace();
                System.out.println("Cannot find connection");
            }

            System.out.println("Connection established: " + connection.toString());
            return connection;
        }
    }

}
