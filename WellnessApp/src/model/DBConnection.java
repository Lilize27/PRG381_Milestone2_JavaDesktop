/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
 
public class DBConnection {
    private static final String URL = "jdbc:derby://localhost:1527/WellnessDB;create=true";
    private static final String USER = "app";
    private static final String PASSWORD = "app";
 
public static Connection getConnection() throws SQLException {
    try {
        Class.forName("org.apache.derby.jdbc.ClientDriver");
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
        throw new SQLException("Derby JDBC driver not found.");
    }

    return DriverManager.getConnection(
        "jdbc:derby://localhost:1527/WellnessDB",
        "app", "app"
    );
}
}

