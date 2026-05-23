package com.eticaret.dao;

import com.eticaret.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AdminDAO {

    public Map<String, Integer> getDashboardStats() {
        Map<String, Integer> stats = new HashMap<>();
        
        String pCount = "SELECT COUNT(*) FROM products";
        String cCount = "SELECT COUNT(*) FROM categories";
        String uCount = "SELECT COUNT(*) FROM users";
        String oCount = "SELECT COUNT(*) FROM orders";
        String pendingCount = "SELECT COUNT(*) FROM orders WHERE status = 'Beklemede'";

        try (Connection conn = DBConnection.getConnection()) {
            
            stats.put("totalProducts", getCount(conn, pCount));
            stats.put("totalCategories", getCount(conn, cCount));
            stats.put("totalUsers", getCount(conn, uCount));
            stats.put("totalOrders", getCount(conn, oCount));
            stats.put("pendingOrders", getCount(conn, pendingCount));

        } catch (SQLException e) {
            System.out.println("Dashboard sayaç hatası: " + e.getMessage());
        }
        return stats;
    }

    private int getCount(Connection conn, String query) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }
}