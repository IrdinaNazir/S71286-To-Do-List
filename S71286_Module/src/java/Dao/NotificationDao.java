/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Dao;

/**
 *
 * @author Irdina Nazir
 */

import model.Notification;
import util.DatabaseUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationDao {
    public List<Notification> getUnreadNotifications(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT ID, taskID, userID, message, created_at FROM notifications WHERE userID = ? AND is_read = false";
        
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Notification n = new Notification();
                    n.setId(rs.getInt("ID"));
                    n.setTaskId(rs.getInt("taskID"));
                    n.setUserId(rs.getInt("userID"));
                    n.setMessage(rs.getString("message"));
                    n.setCreatedAt(rs.getTimestamp("created_at"));
                    notifications.add(n);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching notifications: " + e.getMessage());
            throw new RuntimeException("Database error", e);
        }
        return notifications;
    }
}
