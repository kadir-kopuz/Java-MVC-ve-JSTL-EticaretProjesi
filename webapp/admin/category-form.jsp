<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${empty category ? 'Yeni Kategori' : 'Kategoriyi Düzenle'} - Yönetim Paneli</title>
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
            <a href="categories" class="active">Kategori Yönetimi</a>
            <a href="products">Ürün Yönetimi</a>
            <a href="orders">Sipariş Yönetimi</a>
            <a href="users">Kullanıcı Listesi</a>
        </div>
        <div class="admin-main">
            <h2>${empty category ? 'Yeni Kategori Ekle' : 'Kategoriyi Düzenle'}</h2>
            <p style="color: #777; margin-bottom: 20px;">Lütfen kategori bilgilerini eksiksiz giriniz.</p>
            
            <div class="form-container" style="margin: 0; max-width: 600px; box-shadow: none; border: 1px solid #ddd;">
                <form action="categories" method="POST">
                    <input type="hidden" name="id" value="${category.id}">
                    
                    <div class="form-group">
                        <label>Kategori Adı</label>
                        <input type="text" name="name" value="${category.name}" required placeholder="Örn: Ev & Yaşam">
                    </div>
                    
                    <div style="display: flex; gap: 10px; margin-top: 20px;">
                        <button type="submit" class="btn" style="width: auto; padding: 10px 25px;">Kaydet</button>
                        <a href="categories" class="btn btn-secondary" style="width: auto; padding: 10px 25px; text-decoration: none; text-align: center;">Vazgeç</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</body>
</html>