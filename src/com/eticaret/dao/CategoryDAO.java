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
    private Boolean hasParentCategoryColumnCache;

    public List<Category> getAllActiveCategories() {
        List<Category> list = new ArrayList<>();
        String query = hasParentCategoryColumn()
                ? "SELECT * FROM categories WHERE is_active = TRUE ORDER BY COALESCE(parent_category_id, 0), name"
                : "SELECT * FROM categories WHERE is_active = TRUE ORDER BY name";
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                cat.setDescription(rs.getString("description"));
                if (hasParentCategoryColumn()) {
                    Object parentCategoryId = rs.getObject("parent_category_id");
                    cat.setParentCategoryId(parentCategoryId != null ? ((Number) parentCategoryId).intValue() : null);
                }
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
        String query = hasParentCategoryColumn()
            ? "SELECT * FROM categories ORDER BY COALESCE(parent_category_id, 0), id DESC"
            : "SELECT * FROM categories ORDER BY id DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Category cat = new Category();
                cat.setId(rs.getInt("id"));
                cat.setName(rs.getString("name"));
                cat.setDescription(rs.getString("description"));
                if (hasParentCategoryColumn()) {
                    Object parentCategoryId = rs.getObject("parent_category_id");
                    cat.setParentCategoryId(parentCategoryId != null ? ((Number) parentCategoryId).intValue() : null);
                }
                cat.setActive(rs.getBoolean("is_active"));
                list.add(cat);
            }
        } catch (SQLException e) {
            System.out.println("Admin kategori listeleme hatası: " + e.getMessage());
        }
        return list;
    }

    public boolean addCategory(Category category) {
        String query = "INSERT INTO categories (name) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
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
                    if (hasParentCategoryColumn()) {
                        Object parentCategoryId = rs.getObject("parent_category_id");
                        cat.setParentCategoryId(parentCategoryId != null ? ((Number) parentCategoryId).intValue() : null);
                    }
                    cat.setActive(rs.getBoolean("is_active"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Kategori getirme hatası: " + e.getMessage());
        }
        return cat;
    }

    public boolean updateCategory(Category category) {
        String query = "UPDATE categories SET name = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, category.getName());
            ps.setInt(2, category.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Kategori güncelleme hatası: " + e.getMessage());
            return false;
        }
    }

    public List<Category> getActiveCategoryTree() {
        // Üst kategori kaldırıldı; düz liste döndür
        return getAllActiveCategories();
    }

    public List<Category> getCategoryTree() {
        // Üst kategori kaldırıldı; düz liste döndür
        return getAllCategories();
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

    public boolean hasChildCategories(int categoryId) {
        if (!hasParentCategoryColumn()) {
            return false;
        }

        String query = "SELECT COUNT(*) FROM categories WHERE parent_category_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, categoryId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.out.println("Alt kategori kontrol hatası: " + e.getMessage());
        }
        return false;
    }

    public String deleteOrDeactivateCategory(int categoryId) {
        if (hasProducts(categoryId) || hasChildCategories(categoryId)) {
            String query = hasParentCategoryColumn()
                    ? "UPDATE categories SET is_active = FALSE WHERE id = ? OR parent_category_id = ?"
                    : "UPDATE categories SET is_active = FALSE WHERE id = ?";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, categoryId);
                if (hasParentCategoryColumn()) {
                    ps.setInt(2, categoryId);
                }
                ps.executeUpdate();
                return "Bağlı ürün veya alt kategori olduğu için kategori silinemedi, durumu PASİF olarak güncellendi.";
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

    

    private boolean hasParentCategoryColumn() {
        if (hasParentCategoryColumnCache != null) {
            return hasParentCategoryColumnCache;
        }

        try (Connection conn = DBConnection.getConnection()) {
            ResultSet rs = conn.getMetaData().getColumns(null, null, "categories", "parent_category_id");
            hasParentCategoryColumnCache = rs.next();
            return hasParentCategoryColumnCache;
        } catch (SQLException e) {
            System.out.println("Kategori şema kontrol hatası: " + e.getMessage());
            hasParentCategoryColumnCache = false;
            return false;
        }
    }
}