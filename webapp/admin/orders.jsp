<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sipariş Yönetimi - Yönetim Paneli</title>
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
            <a href="products">Ürün Yönetimi</a>
            <a href="orders" class="active">Sipariş Yönetimi</a>
            <a href="users">Kullanıcı Listesi</a>
        </div>
        <div class="admin-main">
            <h2 style="margin-bottom: 20px;">Sipariş Yönetimi</h2>
            <table class="cart-table" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr style="background: #2c3e50; color: white;">
                        <th style="padding: 12px; text-align: left;">Sipariş ID</th>
                        <th style="padding: 12px; text-align: left;">Müşteri</th>
                        <th style="padding: 12px; text-align: left;">Tarih</th>
                        <th style="padding: 12px; text-align: left;">Toplam Tutar</th>
                        <th style="padding: 12px; text-align: left;">Durum</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="order" items="${orders}">
                        <tr style="border-bottom: 1px solid #ddd;">
                            <td style="padding: 12px;">#${order.id}</td>
                            <td style="padding: 12px;">${order.user.fullName}</td>
                            <td style="padding: 12px;">${order.orderDate}</td>
                            <td style="padding: 12px; font-weight: bold; color: #e74c3c;"><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₺" /></td>
                            <td style="padding: 12px;"><span style="background: #badc58; color: #6ab04c; padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: bold;">Tamamlandı</span></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>