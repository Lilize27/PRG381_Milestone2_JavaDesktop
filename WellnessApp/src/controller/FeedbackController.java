/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import model.Feedback;
import model.DBConnection;

import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Cosmo
 */
public class FeedbackController {
    public void addFeedback(Feedback f) {
        String sql = "INSERT INTO Feedback (studentname, rating, comments) VALUES(?, ?, ?)";
        try(Connection con = DBConnection.getConnection();
                PreparedStatement ps= con.prepareStatement(sql)) {
            
            ps.setString(1, f.getStudent());
            ps.setInt(2, f.getRating());
            ps.setString(3, f.getComments());
            ps.executeUpdate();
            System.out.println("Feedback added successfully.");

        } catch (SQLException e) {
            System.out.println("Error adding feedback: " + e.getMessage());
        }
    }
    
    public ArrayList<Feedback> getAllFeedback() {
    ArrayList<Feedback> list = new ArrayList<>();
    String sql = "SELECT * FROM Feedback";
    
    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
         
        while (rs.next()) {
            int id = rs.getInt("id");
            String student = rs.getString("studentName"); // <- fixed
            int rating = rs.getInt("rating");
            String comments = rs.getString("comments");

            Feedback f = new Feedback(id, student, rating, comments);
            list.add(f);
        }
    } catch (SQLException e) {
        System.out.println("Error fetching feedback: " + e.getMessage());
    }
    return list;
}
    public void updateFeedback(Feedback f) {
        String sql = "UPDATE Feedback SET studentname= ?, rating = ?, comments = ? WHERE id = ?";
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement ps= conn.prepareStatement(sql)) {
            
            ps.setString(1, f.getStudent());
            ps.setInt(2, f.getRating());
            ps.setString(3, f.getComments());
            ps.setInt(4, f.getId());
            
            int rowsUpdated= ps.executeUpdate();
            if(rowsUpdated > 0) {
                System.out.println("Feedback updated successfully.");
            } else {
                System.out.println("No feedback found with that ID");
            }
        } catch (SQLException e) {
            System.out.println("Error updating feedback: " + e.getMessage());
        }
    }
    
     public void deleteFeedback(int id) {
        String sql = "DELETE FROM Feedback WHERE id = ?" ;
        try(Connection conn = DBConnection.getConnection();
                PreparedStatement ps= conn.prepareStatement(sql)) {
            
            ps.setInt(1, id);
            int rowsDeleted = ps.executeUpdate();
            
            if (rowsDeleted >0) {
                System.out.println("Feedback deleted successfully.");
            } else {
                System.out.println("No feedback found with that ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error deleting feedback: " + e.getMessage());
        }
    }
     
     public double getAverageRating() {
    String sql = "SELECT AVG(rating) FROM Feedback";
    try (Connection conn = DBConnection.getConnection();
         Statement st = conn.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
        if (rs.next()) {
            return rs.getDouble(1);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 0.0;
}
   
}
