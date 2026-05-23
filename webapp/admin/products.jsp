<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ürün Yönetimi - Yönetim Paneli</title>
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
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2>Ürün Yönetimi</h2>
                <a href="products?action=new" class="btn" style="width: auto; padding: 10px 20px;">Yeni Ürün Ekle</a>
            </div>
            <table class="cart-table" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr style="background: #2c3e50; color: white;">
                        <th style="padding: 12px; text-align: left;">Görsel</th>
                        <th style="padding: 12px; text-align: left;">Ürün Adı</th>
                        <th style="padding: 12px; text-align: left;">Fiyat</th>
                        <th style="padding: 12px; text-align: left;">Stok</th>
                        <th style="padding: 12px; text-align: left;">İşlemler</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${products}">
                        <tr style="border-bottom: 1px solid #ddd;">
                            <td style="padding: 12px;"><img src="${p.imageUrl}" style="width: 50px; height: 50px; object-fit: cover; border-radius: 4px;"></td>
                            <td style="padding: 12px;">${p.name}</td>
                            <td style="padding: 12px;"><fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₺" /></td>
                            <td style="padding: 12px;">${p.stock} adet</td>
                            <td style="padding: 12px;">
                                <a href="products?action=edit&id=${p.id}" class="btn btn-secondary" style="padding: 5px 10px; font-size: 12px; width: auto; display: inline-block;">Düzenle</a>
                                <a href="products?action=delete&id=${p.id}" class="btn btn-danger" style="padding: 5px 10px; font-size: 12px; width: auto; display: inline-block; background: #e74c3c;" onclick="return confirm('Bu ürünü silmek istediğinize emin misiniz?')">Sil</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>