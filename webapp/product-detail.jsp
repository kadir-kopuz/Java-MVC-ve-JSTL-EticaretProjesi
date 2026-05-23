<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${product.name} - Ürün Detayı</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .detail-container { max-width: 900px; margin: 40px auto; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); display: flex; gap: 40px; }
        .detail-image { width: 50%; }
        .detail-image img { width: 100%; max-height: 400px; object-fit: cover; border-radius: 8px; }
        .detail-info { width: 50%; display: flex; flex-direction: column; justify-content: center; }
        .detail-title { font-size: 28px; font-weight: bold; color: #2c3e50; margin-bottom: 15px; }
        .detail-desc { font-size: 16px; color: #666; margin-bottom: 20px; line-height: 1.6; }
        .detail-price { font-size: 24px; font-weight: bold; color: #e74c3c; margin-bottom: 15px; }
        .detail-stock { font-size: 14px; margin-bottom: 25px; font-weight: bold; }
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
                    <a href="#" style="font-weight: bold; color: #2ecc71;">${currentUser.fullName}</a>
                    <a href="logout" style="color: #e74c3c;">Çıkış Yap</a>
                </c:when>
                <c:otherwise>
                    <a href="login">Giriş Yap</a>
                    <a href="register">Kayıt Ol</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>

    <div class="detail-container">
        <div class="detail-image">
            <img src="${empty product.imageUrl ? 'https://via.placeholder.com/400x400?text=Gorsel+Yok' : product.imageUrl}" alt="${product.name}">
        </div>
        <div class="detail-info">
            <div class="detail-title">${product.name}</div>
            <div class="detail-desc">${product.description}</div>
            
            <div class="detail-price">
                <fmt:formatNumber value="${product.price}" type="currency" currencySymbol="₺" />
            </div>

            <div class="detail-stock">
                <c:choose>
                    <c:when test="${product.stock > 0}">
                        <span style="color: #2ecc71;">Stok Durumu: Mağazada Mevcut (${product.stock} adet)</span>
                    </c:when>
                    <c:otherwise>
                        <span style="color: #e74c3c;">Stok Durumu: Stokta Yok!</span>
                    </c:otherwise>
                </c:choose>
            </div>

            <c:choose>
                <c:when test="${product.stock > 0}">
                    <a href="cart?action=add&id=${product.id}" class="btn" style="text-align:center;">Sepete Ekle</a>
                </c:when>
                <c:otherwise>
                    <button class="btn" style="background: #bdc3c7; cursor: not-allowed;" disabled>Stokta Olmadığı İçin Eklenemez</button>
                </c:otherwise>
            </c:choose>
            
            <a href="home" style="margin-top: 15px; text-align: center; color: #7f8c8d; text-decoration: none; font-size: 14px;">← Ürünlere Geri Dön</a>
        </div>
    </div>

</body>
</html>