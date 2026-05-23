<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Siparişlerim - E-Ticaret Portalı</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .orders-container { max-width: 1000px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        .order-box { border: 1px solid #ddd; border-radius: 6px; margin-bottom: 20px; overflow: hidden; }
        .order-header { background: #f8f9fa; padding: 15px; display: flex; justify-content: space-between; border-bottom: 1px solid #ddd; font-size: 14px; color: #555; }
        .order-header strong { color: #2c3e50; }
        .order-body { padding: 15px; }
        .order-item { display: flex; justify-content: space-between; padding: 8px 0; border-bottom: 1px dashed #eee; }
        .order-item:last-child { border-bottom: none; }
        .status-badge { padding: 4px 8px; border-radius: 4px; font-weight: bold; font-size: 12px; }
        .status-pending { background: #ffeaa7; color: #d63031; }
        .status-completed { background: #badc58; color: #6ab04c; }
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

    <div class="orders-container">
        <h2 style="margin-bottom: 25px; color: #2c3e50;">Geçmiş Siparişleriniz</h2>
        
        <c:choose>
            <c:when test="${not empty orders}">
                <c:forEach var="order" items="${orders}">
                    <div class="order-box">
                        <div class="order-header">
                            <div>Sipariş Tarihi: <strong>${order.orderDate}</strong></div>
                            <div>Toplam Tutar: <strong style="color: #e74c3c;"><fmt:formatNumber value="${order.totalPrice}" type="currency" currencySymbol="₺" /></strong></div>
                            <div>Durum: <span class="status-badge status-completed">Tamamlandı</span></div>
                        </div>
                        <div class="order-body">
                            <c:forEach var="item" items="${order.items}">
                                <div class="order-item">
                                    <span>${item.product.name} <strong>(x${item.quantity})</strong></span>
                                    <span><fmt:formatNumber value="${item.totalPrice}" type="currency" currencySymbol="₺" /></span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:forEach>
            </c:when>
            <c:otherwise>
                <p style="text-align: center; color: #777; font-size: 18px; margin: 40px 0;">Henüz hiç siparişiniz bulunmamaktadır.</p>
                <div style="text-align: center;">
                    <a href="home" class="btn" style="width: auto; display: inline-block;">Alışverişe Başla</a>
                </div>
            </c:otherwise>
        </c:choose>
    </div>

</body>
</html>