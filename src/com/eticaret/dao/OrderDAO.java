package com.eticaret.dao;

import com.eticaret.model.CartItem;
import com.eticaret.model.Order;
import com.eticaret.model.OrderItem;
import com.eticaret.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public boolean createOrder(Order order, List<CartItem> cartItems) {
        Connection conn = null;
        PreparedStatement psOrder = null;
        PreparedStatement psItem = null;
        PreparedStatement psStock = null;
        ResultSet rs = null;

        String insertOrder = "INSERT INTO orders (user_id, total_amount, status) VALUES (?, ?, 'Beklemede')";
        String insertItem = "INSERT INTO order_items (order_id, product_id, quantity, unit_price, subtotal) VALUES (?, ?, ?, ?, ?)";
        String updateStock = "UPDATE products SET stock = stock - ? WHERE id = ?";

        try {
            conn = DBConnection.getConnection();
            conn.setAutoCommit(false); 

            psOrder = conn.prepareStatement(insertOrder, Statement.RETURN_GENERATED_KEYS);
            psOrder.setInt(1, order.getUserId());
            psOrder.setDouble(2, order.getTotalAmount());
            psOrder.executeUpdate();

            rs = psOrder.getGeneratedKeys();
            int orderId = 0;
            if (rs.next()) {
                orderId = rs.getInt(1);
            }


            psItem = conn.prepareStatement(insertItem);
            psStock = conn.prepareStatement(updateStock);

            for (CartItem item : cartItems) {
                psItem.setInt(1, orderId);
                psItem.setInt(2, item.getProduct().getId());
                psItem.setInt(3, item.getQuantity());
                psItem.setDouble(4, item.getProduct().getPrice());
                psItem.setDouble(5, item.getSubtotal());
                psItem.addBatch();

                // Stok azaltma sorgusu
                psStock.setInt(1, item.getQuantity());
                psStock.setInt(2, item.getProduct().getId());
                psStock.addBatch();
            }

            psItem.executeBatch();
            psStock.executeBatch();

            conn.commit(); 
            return true;

        } catch (SQLException e) {
            System.out.println("Sipariş oluşturma transaction hatası: " + e.getMessage());
            if (conn != null) {
                try {
                    conn.rollback(); 
                } catch (SQLException ex) {
                    System.out.println("Rollback hatası: " + ex.getMessage());
                }
            }
            return false;
        } finally {
            try {
                if (rs != null) rs.close();
                if (psOrder != null) psOrder.close();
                if (psItem != null) psItem.close();
                if (psStock != null) psStock.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                System.out.println("Kaynak kapatma hatası: " + e.getMessage());
            }
        }
    }


    public List<Order> getOrdersByUserId(int userId) {
        List<Order> list = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Order o = new Order();
                    o.setId(rs.getInt("id"));
                    o.setUserId(rs.getInt("user_id"));
                    o.setOrderDate(rs.getTimestamp("order_date"));
                    o.setTotalAmount(rs.getDouble("total_amount"));
                    o.setStatus(rs.getString("status"));
                    list.add(o);
                }
            }
        } catch (SQLException e) {
            System.out.println("Sipariş listeleme hatası: " + e.getMessage());
        }
        return list;
    }
    public List<Order> getAllOrders() {
        List<Order> list = new ArrayList<>();
        String query = "SELECT o.*, u.full_name FROM orders o " +
                       "INNER JOIN users u ON o.user_id = u.id ORDER BY o.order_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Order o = new Order();
                o.setId(rs.getInt("id"));
                o.setUserId(rs.getInt("user_id"));
                o.setOrderDate(rs.getTimestamp("order_date"));
                o.setTotalAmount(rs.getDouble("total_amount"));
                o.setStatus(rs.getString("status"));
                o.setCustomerName(rs.getString("full_name")); // JOIN'den gelen veri
                list.add(o);
            }
        } catch (SQLException e) {
            System.out.println("Admin tüm siparişleri listeleme hatası: " + e.getMessage());
        }
        return list;
    }

    public boolean updateOrderStatus(int orderId, String newStatus) {
        String query = "UPDATE orders SET status = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setString(1, newStatus);
            ps.setInt(2, orderId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Sipariş durum güncelleme hatası: " + e.getMessage());
            return false;
        }
    }
}