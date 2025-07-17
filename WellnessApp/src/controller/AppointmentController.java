/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Appointment;
import model.DBConnection;

import java.sql.*;
import java.util.ArrayList;

public class AppointmentController {

    public boolean addAppointment(Appointment app) {
        String sql = "INSERT INTO Appointments (studentName, counsellorName, date, time, status) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, app.getStudentName());
            stmt.setString(2, app.getCounsellorName());
            stmt.setString(3, app.getDate());
            stmt.setString(4, app.getTime());
            stmt.setString(5, app.getStatus());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Add error: " + e.getMessage());
            return false;
        }
    }

   public ArrayList<Appointment> getAppointments() {
    ArrayList<Appointment> list = new ArrayList<>();
    String sql = "SELECT * FROM Appointments";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            list.add(new Appointment(
                rs.getInt("id"), 
                rs.getString("studentName"),
                rs.getString("counsellorName"),
                rs.getString("date"),
                rs.getString("time"),
                rs.getString("status")
            ));
        }

    } catch (SQLException e) {
        System.out.println("Fetch error: " + e.getMessage());
    }
    return list;
}

    public boolean deleteAppointment(int id) {
    String sql = "DELETE FROM Appointments WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, id);
        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Delete error: " + e.getMessage());
        return false;
    }
}

    public boolean updateAppointment(Appointment app) {
    String sql = "UPDATE Appointments SET counsellorName=?, time=?, status=? WHERE id=?";
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, app.getCounsellorName());
        stmt.setString(2, app.getTime());
        stmt.setString(3, app.getStatus());
        stmt.setInt(4, app.getID()); // WHERE id = ?

        return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
        System.out.println("Update error: " + e.getMessage());
        return false;
    }
}

    public ArrayList<String> getCounsellors() {
        ArrayList<String> list = new ArrayList<>();
        String sql = "SELECT name FROM Counselors";
        try (Connection conn = DBConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println("Counsellor fetch error: " + e.getMessage());
        }
        return list;
    }
    
    public ArrayList<String> getAllStudentNames() {
    ArrayList<String> students = new ArrayList<>();
    String sql = "SELECT DISTINCT studentName FROM Appointments";

    try (Connection conn = DBConnection.getConnection();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery(sql)) {

        while (rs.next()) {
            students.add(rs.getString("studentName"));
        }
    } catch (SQLException e) {
        System.out.println("Error fetching students: " + e.getMessage());
    }
    return students;
}
    
    public int getTotalAppointments() {
    String sql = "SELECT COUNT(*) FROM Appointments";
    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) {
            return rs.getInt(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0;
}
}
