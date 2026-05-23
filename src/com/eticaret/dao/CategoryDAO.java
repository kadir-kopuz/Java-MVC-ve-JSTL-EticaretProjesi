package com.eticaret.dao;

import com.eticaret.model.Category;
import com.eticaret.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {
    public List<Category> getAllActiveCategories() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM categories WHERE is_active = TRUE";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                cat.setDescription(rs.getString("description"));
                cat.setActive(rs.getBoolean("is_active"));
                list.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Kategori listeleme hatası: " + e.getMessage());
        }
        return list;
    }
    public List<Category> getAllCategories() {
        List<Category> list = new ArrayList<>();
        String query = "SELECT * FROM categories ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                cat.setDescription(rs.getString("description"));
                cat.setActive(rs.getBoolean("is_active"));
                list.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Admin kategori listeleme hatası: " + e.getMessage());
        }
        return list;
    }

    public boolean addCategory(Category category) {
        String query = "INSERT INTO categories (name, description, is_active) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isActive());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kategori ekleme hatası: " + e.getMessage());
            return false;
        }
    }

    public Category getCategoryById(int id) {
        Category cat = null;
        String query = "SELECT * FROM categories WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    cat = new Category();
                    cat.setId(rs.getInt("id"));
                    cat.setName(rs.getString("name"));
                    cat.setDescription(rs.getString("description"));
                    cat.setActive(rs.getBoolean("is_active"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Kategori getirme hatası: " + e.getMessage());
        }
        return cat;
    }

    public boolean updateCategory(Category category) {
        String query = "UPDATE categories SET name = ?, description = ?, is_active = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setString(2, category.getDescription());
            ps.setBoolean(3, category.isActive());
            ps.setInt(4, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kategori güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public boolean hasProducts(int categoryId) {
        String query = "SELECT COUNT(*) FROM products WHERE category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Kategori ürün kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    public String deleteOrDeactivateCategory(int categoryId) {
        if (hasProducts(categoryId)) {
            String query = "UPDATE categories SET is_active = FALSE WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, categoryId);
                ps.executeUpdate();
                return "Bağlı ürünler olduğu için kategori silinemedi, durumu PASİF olarak güncellendi.";
            } catch (SQLException e) {
                return "İşlem sırasında hata oluştu.";
            }
        } else {
            String query = "DELETE FROM categories WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, categoryId);
                ps.executeUpdate();
                return "Kategori başarıyla veritabanından silindi.";
            } catch (SQLException e) {
                return "Silme işlemi sırasında hata oluştu.";
            }
        }
    }
}