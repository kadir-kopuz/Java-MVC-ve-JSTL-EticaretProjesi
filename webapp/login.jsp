<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Giriş Yap - E-Ticaret Portalı</title>
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
        <h2>Kullanıcı Girişi</h2>
        
        <c:if test="${not empty error}">
            <div class="error-msg">${error}</div>
        </c:if>
        
        <form action="login" method="POST">
            <div class="form-group">
                <label>E-posta Adresi</label>
                <input type="email" name="email" required placeholder="ornek@mail.com">
            </div>
            <div class="form-group">
                <label>Şifre</label>
                <input type="password" name="password" required placeholder="******">
            </div>
            <button type="submit" class="btn">Giriş Yap</button>
        </form>
        <p style="text-align: center; margin-top: 15px; font-size: 14px;">
            Hesabınız yok mu? <a href="register" style="color: #3498db; text-decoration: none;">Kayıt Olun</a>
        </p>
    </div>

</body>
</html>