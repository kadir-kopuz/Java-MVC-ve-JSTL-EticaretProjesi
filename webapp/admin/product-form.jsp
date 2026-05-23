<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty product ? 'Yeni Ürün' : 'Ürünü Düzenle'} - Yönetim Paneli</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
</head>
<body>
    <div class="navbar" style="background-color: #34495e;">
        <a href="dashboard" class="logo">Yönetici Paneli</a>
        <div>
            <a href="../home" target="_blank" style="color: #3498db;">Sitede Gör →</a>
            <a href="../logout" style="color: #e74c3c;">Güvenli Çıkış</a>
        </div>
    </div>
    <div class="admin-container">
        <div class="admin-sidebar">
            <a href="dashboard">Panel Ana Sayfa</a>
            <a href="categories">Kategori Yönetimi</a>
            <a href="products" class="active">Ürün Yönetimi</a>
            <a href="orders">Sipariş Yönetimi</a>
            <a href="users">Kullanıcı Listesi</a>
        </div>
        <div class="admin-main">
            <h2>${empty product ? 'Yeni Ürün Ekle' : 'Ürünü Düzenle'}</h2>
            <p style="color: #777; margin-bottom: 20px;">Ürün detaylarını, fiyatını ve stok durumunu bu ekrandan yönetebilirsiniz.</p>
            
            <div class="form-container" style="margin: 0; max-width: 700px; box-shadow: none; border: 1px solid #ddd;">
                <form action="products" method="POST">
                    <input type="hidden" name="id" value="${product.id}">
                    
                    <div class="form-group">
                        <label>Ürün Adı</label>
                        <input type="text" name="name" value="${product.name}" required placeholder="Ürün başlığı girin">
                    </div>
                    
                    <div class="form-group">
                        <label>Ürün Açıklaması</label>
                        <textarea name="description" required placeholder="Ürün özelliklerini buraya yazın..." style="width: 100%; height: 100px; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; resize: vertical;">${product.description}</textarea>
                    </div>
                    
                    <div style="display: flex; gap: 20px;">
                        <div class="form-group" style="flex: 1;">
                            <label>Fiyat (₺)</label>
                            <input type="number" step="0.01" name="price" value="${product.price}" required placeholder="0.00">
                        </div>
                        <div class="form-group" style="flex: 1;">
                            <label>Stok Adedi</label>
                            <input type="number" name="stock" value="${product.stock}" required placeholder="0">
                        </div>
                    </div>
                    
                    <div class="form-group">
                        <label>Kategori Seçimi</label>
                        <select name="categoryId" required style="width: 100%; padding: 10px; border: 1px solid #ccc; border-radius: 4px; background: white;">
                            <option value="">-- Kategori Seçiniz --</option>
                            <c:forEach var="cat" items="${categories}">
                                <option value="${cat.id}" ${product.categoryId == cat.id ? 'selected' : ''}>
                                    ${cat.name}
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label>Ürün Görsel Linki (URL)</label>
                        <input type="url" name="imageUrl" value="${product.imageUrl}" placeholder="https://example.com/resim.jpg">
                    </div>
                    
                    <div style="display: flex; gap: 10px; margin-top: 25px;">
                        <button type="submit" class="btn" style="width: auto; padding: 10px 25px;">Ürünü Kaydet</button>
                        <a href="products" class="btn btn-secondary" style="width: auto; padding: 10px 25px; text-decoration: none; text-align: center;">İptal</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>