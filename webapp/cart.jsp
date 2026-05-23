<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Sepetim - E-Ticaret Portalı</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .cart-container { max-width: 900px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        .cart-table { width: 100%; border-collapse: collapse; margin-bottom: 20px; }
        .cart-table th, .cart-table td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }
        .cart-table th { background-color: #2c3e50; color: white; }
        .cart-total { text-align: right; font-size: 20px; font-weight: bold; margin-bottom: 20px; color: #e74c3c; }
        .cart-actions { display: flex; justify-content: space-between; }
        .btn-danger { background: #e74c3c; }
        .btn-danger:hover { background: #c0392b; }
        .btn-success { background: #2ecc71; }
        .btn-success:hover { background: #27ae60; }
    </style>
</head>
<body>

    <div class="navbar">
        <a href="home" class="logo">E-Ticaret Portalı</a>
        <div>
            <a href="home">Ürünler</a>
            <a href="cart.jsp">Sepetim</a>
            <c:choose>
                <c:when test="${not empty currentUser}">
                    <a href="my-orders">Siparişlerim</a>
                    <a href="#" style="font-weight: bold; color: #2ecc71;">Hoş geldin, ${currentUser.fullName}</a>
                    <a href="logout" style="color: #e74c3c;">Çıkış Yap</a>
                </c:when>
                <c:otherwise>
                    <a href="login">Giriş Yap</a>
                    <a href="register">Kayıt Ol</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="cart-container">
        <h2 style="margin-bottom: 20px; color: #2c3e50;">Alışveriş Sepetiniz</h2>
        
        <c:choose>
            <c:when test="${not empty cart}">
                <table class="cart-table">
                    <thead>
                        <tr>
                            <th>Ürün Bilgisi</th>
                            <th>Fiyat</th>
                            <th>Adet</th>
                            <th>Toplam</th>
                            <th>İşlem</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="item" items="${cart}">
                            <tr>
                                <td>${item.product.name}</td>
                                <td><fmt:formatNumber value="${item.product.price}" type="currency" currencySymbol="₺" /></td>
                                <td>${item.quantity}</td>
                                <td><fmt:formatNumber value="${item.subtotal}" type="currency" currencySymbol="₺" /></td>
                                <td>
                                    <a href="cart?action=remove&id=${item.product.id}" class="btn btn-danger" style="padding: 5px 10px; font-size: 12px; width: auto;">Sil</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                
                <div class="cart-total">
                    Genel Toplam: <fmt:formatNumber value="${grandTotal}" type="currency" currencySymbol="₺" />
                </div>
                
                <div class="cart-actions">
                    <a href="home" class="btn btn-secondary" style="width: auto;">Alışverişe Devam Et</a>
                    <a href="order?action=checkout" class="btn btn-success" style="width: auto;">Siparişi Tamamla</a>
                </div>
            </c:when>
            <c:otherwise>
                <p style="text-align: center; color: #777; font-size: 18px; margin: 40px 0;">Sepetiniz henüz boş.</p>
                <div style="text-align: center;">
                    <a href="home" class="btn" style="width: auto; display: inline-block;">Ürünleri İncele</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>