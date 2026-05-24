<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Kayıt Ol - E-Ticaret Portalı</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <div class="navbar">
        <a href="home" class="logo">E-Ticaret Portalı</a>
        <div>
            <a href="home">Ürünler</a>
            <a href="cart.jsp">Sepetim</a>
            <a href="login">Giriş Yap</a>
            <a href="register">Kayıt Ol</a>
        </div>
    </div>

    <div class="form-container">
        <h2>Yeni Üye Kaydı</h2>
        
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="error-msg">${errorMessage}</div>
        </c:if>
        
        <form action="register" method="POST">
            <div class="form-group">
                <label>Adınız Soyadınız</label>
                <input type="text" name="fullName" required placeholder="Ahmet Yılmaz">
            </div>
            <div class="form-group">
                <label>E-posta Adresi</label>
                <input type="email" name="email" required placeholder="ahmet@gmail.com">
            </div>
            <div class="form-group">
                <label>Telefon</label>
                <input type="tel" name="phone" required placeholder="05XX XXX XX XX">
            </div>
            <div class="form-group">
                <label>Adres</label>
                <textarea name="address" required placeholder="Açık adresinizi giriniz" style="width: 100%; min-height: 90px; padding: 10px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; resize: vertical;"></textarea>
            </div>
            <div class="form-group">
                <label>Şifre</label>
                <input type="password" name="password" required placeholder="******">
            </div>
            <button type="submit" class="btn">Kayıt Ol</button>
        </form>
        <p style="text-align: center; margin-top: 15px; font-size: 14px;">
            Zaten üye misiniz? <a href="login" style="color: #3498db; text-decoration: none;">Giriş Yapın</a>
        </p>
    </div>

</body>
</html>