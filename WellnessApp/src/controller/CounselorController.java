/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Counselor;
import model.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Cosmo
 */
public class CounselorController {
    
    public void addCounselor(Counselor c) {
    try (Connection conn = DBConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement("INSERT INTO COUNSELORS (name, specialization, available) VALUES (?, ?, ?)")) {

        ps.setString(1, c.getName());
        ps.setString(2, c.getSpecialization());
        ps.setInt(3, c.getAvailability() ? 1 : 0); 
        ps.executeUpdate();

    } catch (SQLException e) {
        System.out.println("Error adding counselor: " + e.getMessage());
    }
}

public ArrayList<Counselor> getAllCounselors() {
    
    ArrayList<Counselor> list = new ArrayList<>();
    String sql = "SELECT * FROM COUNSELORS";
    
    try (Connection conn = DBConnection.getConnection();

         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {

        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            String specialization = rs.getString("specialization");
            boolean available = rs.getInt("available") == 1;
            
           Counselor counselor = new Counselor(id, name, specialization, available);
                list.add(counselor);
        }

    } catch (SQLException e) {
        System.out.println("Error fetching counselors: " + e.getMessage());
    }
    return list;
}

public void updateCounselor(Counselor c) {
    String sql = "UPDATE COUNSELORS SET name = ?, specialization = ?, available = ? WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getName());
            ps.setString(2, c.getSpecialization());
            ps.setInt(3, c.getAvailability() ? 1 : 0);
            ps.setInt(4, c.getId());

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Counselor updated successfully.");
            } else {
                System.out.println("No counselor found with that ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error updating counselor: " + e.getMessage());
        }
    }
        
    public void deleteCounselor(int id) {
        String sql = "DELETE FROM Counselors WHERE id = ?";

        try (Connection conn = DBConnection.getConnection();

             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Counselor deleted successfully.");
            } else {
                System.out.println("No counselor found with that ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error deleting counselor: " + e.getMessage());
        }
    
    }
    
    public int getTotalCounselors() {
    String sql = "SELECT COUNT(*) FROM Counselors";
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

