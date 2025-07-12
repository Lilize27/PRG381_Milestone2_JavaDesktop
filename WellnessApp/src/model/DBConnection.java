/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.sql.*;
/**
 *
 * @author Cosmo
 */
public class DBConnection {
     private static Connection conn;

    public static Connection getConnection() throws SQLException {
        if (conn == null) {
            try {
                String dbURL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
                conn = DriverManager.getConnection(dbURL);
            } catch (SQLException ex) {
                throw new SQLException("Database connection failed: " + ex.getMessage());
            }
        }
        return conn;
    }
}
