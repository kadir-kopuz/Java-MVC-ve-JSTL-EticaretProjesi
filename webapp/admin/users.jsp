<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kullanıcı Listesi - Yönetim Paneli</title>
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
            <a href="orders">Sipariş Yönetimi</a>
            <a href="users" class="active">Kullanıcı Listesi</a>
        </div>
        <div class="admin-main">
            <h2 style="margin-bottom: 20px;">Sistemde Kayıtlı Kullanıcılar</h2>
            <table class="cart-table" style="width: 100%; border-collapse: collapse;">
                <thead>
                    <tr style="background: #2c3e50; color: white;">
                        <th style="padding: 12px; text-align: left;">Kullanıcı ID</th>
                        <th style="padding: 12px; text-align: left;">Ad Soyad</th>
                        <th style="padding: 12px; text-align: left;">E-posta</th>
                        <th style="padding: 12px; text-align: left;">Telefon</th>
                        <th style="padding: 12px; text-align: left;">Rol</th>
                        <th style="padding: 12px; text-align: left;">Kayıt Tarihi</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="u" items="${users}">
                        <tr style="border-bottom: 1px solid #ddd;">
                            <td style="padding: 12px;">${u.id}</td>
                            <td style="padding: 12px;">${u.fullName}</td>
                            <td style="padding: 12px;">${u.email}</td>
                            <td style="padding: 12px;">${u.phone}</td>
                            <td style="padding: 12px;">
                                <span style="padding: 3px 8px; border-radius: 4px; font-size: 12px; font-weight: bold; ${u.role == 'ADMIN' ? 'background: #ffeaa7; color: #d63031;' : 'background: #dff9fb; color: #130cb7;'}">
                                    ${u.role}
                                </span>
                            </td>
                            <td style="padding: 12px;">
                                <fmt:formatDate value="${u.createdAt}" pattern="dd.MM.yyyy HH:mm" />
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>