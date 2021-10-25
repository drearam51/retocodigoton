/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package base_datos_1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 *
 * @author alramirez
 */
public class ConnectionManager {

    private static Connection con;
    private static String driver = "com.mysql.jdbc.Driver";
    private static String database = "evalart_reto";
    private static String hostname = "localhost";
    private static String port = "3306";
    private static String url = "jdbc:mysql://" + hostname + ":" + port + "/" + database;
    private static String username = "xxxxxxxxxx";
    private static String password = "XXXXXXXXXX";
    

    public static Connection getConnection() {
        try {
            Class.forName(driver);
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                // log an exception. fro example:
                System.out.println("Failed to create the database connection.");
            }
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found.");
        }
        return con;
    }
}