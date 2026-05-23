package com.eticaret.dao;

import com.eticaret.model.Product;
import com.eticaret.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public List<Product> getAllActiveProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM products WHERE is_active = TRUE";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setImageUrl(rs.getString("image_url"));
                p.setActive(rs.getBoolean("is_active"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Ürün listeleme hatası: " + e.getMessage());
        }
        return list;
    }

    public List<Product> getProductsByCategory(int categoryId) {
        List<Product> list = new ArrayList<>();
        String query = "SELECT * FROM products WHERE category_id = ? AND is_active = TRUE";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setActive(rs.getBoolean("is_active"));
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Kategoriye göre ürün getirme hatası: " + e.getMessage());
        }
        return list;
    }

    public Product getProductById(int id) {
        Product p = null;
        String query = "SELECT * FROM products WHERE id = ?";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    p = new Product();
                    p.setId(rs.getInt("id"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setName(rs.getInt("id") + " " + rs.getString("name")); // veya direkt ismini ata
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setStock(rs.getInt("stock"));
                    p.setImageUrl(rs.getString("image_url"));
                    p.setActive(rs.getBoolean("is_active"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Ürün detay hatası: " + e.getMessage());
        }
        return p;
    }
    // Admin için aktif-pasif ayrımı yapmaksızın TÜM ürünleri listeleme (İster 6.4)
    public List<Product> getAllProducts() {
        List<Product> list = new ArrayList<>();
        String query = "SELECT p.*, c.name as category_name FROM products p " +
                       "INNER JOIN categories c ON p.category_id = c.id ORDER BY p.id DESC";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setPrice(rs.getDouble("price"));
                p.setStock(rs.getInt("stock"));
                p.setImageUrl(rs.getString("image_url"));
                p.setActive(rs.getBoolean("is_active"));
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println("Admin ürün listeleme hatası: " + e.getMessage());
        }
        return list;
    }

    // Yeni Ürün Ekleme
    public boolean addProduct(Product product) {
        String query = "INSERT INTO products (category_id, name, description, price, stock, image_url, is_active) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ürün ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Ürün Güncelleme
    public boolean updateProduct(Product product) {
        String query = "UPDATE products SET category_id = ?, name = ?, description = ?, price = ?, stock = ?, image_url = ?, is_active = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            
            ps.setInt(1, product.getCategoryId());
            ps.setString(2, product.getName());
            ps.setString(3, product.getDescription());
            ps.setDouble(4, product.getPrice());
            ps.setInt(5, product.getStock());
            ps.setString(6, product.getImageUrl());
            ps.setBoolean(7, product.isActive());
            ps.setInt(8, product.getId());
            
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ürün güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    // Ürün Silme
    public boolean deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Ürün silme hatası: " + e.getMessage());
            return false;
        }
    }
}