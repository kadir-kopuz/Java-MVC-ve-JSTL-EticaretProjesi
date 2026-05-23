<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ana Sayfa - E-Ticaret Portalı</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <style>
        .container { display: flex; margin: 20px; gap: 20px; }
        .sidebar { width: 25%; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); height: fit-content; }
        .sidebar h3 { margin-bottom: 15px; color: #2c3e50; border-bottom: 2px solid #f4f7f6; padding-bottom: 5px; }
        .sidebar ul { list-style: none; }
        .sidebar ul li { margin-bottom: 10px; }
        .sidebar ul li a { text-decoration: none; color: #555; font-weight: 600; display: block; padding: 5px; border-radius: 4px; }
        .sidebar ul li a:hover, .sidebar ul li a.active { background: #3498db; color: white; }
        
        .main-content { width: 75%; }
        .product-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(250px, 1fr)); gap: 20px; }
        .product-card { background: white; padding: 15px; border-radius: 8px; box-shadow: 0 2px 5px rgba(0,0,0,0.1); display: flex; flex-direction: column; justify-content: space-between; }
        .product-card img { width: 100%; height: 180px; object-fit: cover; border-radius: 4px; margin-bottom: 10px; }
        .product-title { font-size: 18px; font-weight: bold; margin-bottom: 5px; color: #2c3e50; }
        .product-desc { font-size: 14px; color: #777; margin-bottom: 10px; height: 45px; overflow: hidden; }
        .product-price { font-size: 16px; font-weight: bold; color: #e74c3c; margin-bottom: 10px; }
        .product-stock { font-size: 12px; margin-bottom: 15px; font-weight: bold; }
        .actions { display: flex; gap: 10px; }
        .btn-secondary { background: #7f8c8d; }
        .btn-secondary:hover { background: #95a5a6; }
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

    <div class="container">
        <div class="sidebar">
            <h3>Kategoriler</h3>
            <ul>
                <li>
                    <a href="home" class="${empty selectedCategory ? 'active' : ''}">Tüm Ürünler</a>
                </li>
                <c:forEach var="cat" items="${categories}">
                    <li>
                        <a href="home?category=${cat.id}" class="${selectedCategory == cat.id ? 'active' : ''}">
                            ${cat.name}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </div>

        <div class="main-content">
            <h2 style="margin-bottom: 20px; color: #2c3e50;">Ürünlerimiz</h2>
            
            <div class="product-grid">
                <c:forEach var="p" items="${products}">
                    <div class="product-card">
                        <div>
                            <img src="${empty p.imageUrl ? 'https://via.placeholder.com/250x180?text=Gorsel+Yok' : p.imageUrl}" alt="${p.name}">
                            <div class="product-title">${p.name}</div>
                            <div class="product-desc">${p.description}</div>
                        </div>
                        
                        <div>
                            <div class="product-price">
                                <fmt:formatNumber value="${p.price}" type="currency" currencySymbol="₺" />
                            </div>
                            
                            <div class="product-stock">
                                <c:choose>
                                    <c:when test="${p.stock > 0}">
                                        <span style="color: #2ecc71;">Stokta Var (${p.stock} adet)</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span style="color: #e74c3c;">Stokta Yok</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>

                            <div class="actions">
                                <a href="product-detail?id=${p.id}" class="btn btn-secondary" style="padding: 8px; font-size: 13px;">Detay</a>
                                
                                <c:choose>
                                    <c:when test="${p.stock > 0}">
                                        <a href="cart?action=add&id=${p.id}" class="btn" style="padding: 8px; font-size: 13px;">Sepete Ekle</a>
                                    </c:when>
                                    <c:otherwise>
                                        <button class="btn" style="padding: 8px; font-size: 13px; background: #bdc3c7; cursor: not-allowed;" disabled>Stokta Yok</button>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <c:if test="${empty products}">
                <p style="text-align: center; margin-top: 40px; color: #777; font-size: 18px;">Bu kategoride henüz ürün bulunmamaktadır.</p>
            </c:if>
        </div>
    </div>

</body>
</html>