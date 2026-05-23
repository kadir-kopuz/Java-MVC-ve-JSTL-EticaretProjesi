<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Yönetim Paneli - E-Ticaret</title>
    <link rel="stylesheet" type="text/css" href="../css/style.css">
    <style>
        .admin-container { display: flex; margin: 20px; gap: 20px; }
        .admin-sidebar { width: 20%; background: #2c3e50; padding: 20px; min-height: 80vh; border-radius: 8px; }
        .admin-sidebar a { display: block; color: #ecf0f1; text-decoration: none; padding: 12px; margin-bottom: 5px; border-radius: 4px; font-weight: 600; }
        .admin-sidebar a:hover, .admin-sidebar a.active { background: #34495e; color: #3498db; }
        
        .admin-main { width: 80%; background: white; padding: 30px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.05); }
        .stats-grid { display: grid; grid-template-columns: repeat(auto-fill, minmax(180px, 1fr)); gap: 20px; margin-top: 20px; }
        .stat-card { background: #f8f9fa; border-left: 5px solid #3498db; padding: 20px; border-radius: 4px; text-align: center; box-shadow: 0 2px 4px rgba(0,0,0,0.02); }
        .stat-card h3 { font-size: 14px; color: #7f8c8d; text-transform: uppercase; margin-bottom: 10px; }
        .stat-card .num { font-size: 28px; font-weight: bold; color: #2c3e50; }
    </style>
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
            <a href="dashboard" class="active">Panel Ana Sayfa</a>
            <a href="categories">Kategori Yönetimi</a>
            <a href="products">Ürün Yönetimi</a>
            <a href="orders">Sipariş Yönetimi</a>
            <a href="users">Kullanıcı Listesi</a>
        </div>

        <div class="admin-main">
            <h2>Sistem Genel Özeti</h2>
            <p style="color: #777;">Sistemde kayıtlı olan güncel verilerin durum özetleri aşağıda yer almaktadır.</p>
            
            <div class="stats-grid">
                <div class="stat-card">
                    <h3>Toplam Ürün</h3>
                    <div class="num">${stats.totalProducts}</div>
                </div>
                <div class="stat-card" style="border-left-color: #2ecc71;">
                    <h3>Toplam Kategori</h3>
                    <div class="num">${stats.totalCategories}</div>
                </div>
                <div class="stat-card" style="border-left-color: #9b59b6;">
                    <h3>Kayıtlı Müşteri</h3>
                    <div class="num">${stats.totalUsers}</div>
                </div>
                <div class="stat-card" style="border-left-color: #f1c40f;">
                    <h3>Toplam Sipariş</h3>
                    <div class="num">${stats.totalOrders}</div>
                </div>
                <div class="stat-card" style="border-left-color: #e74c3c;">
                    <h3>Bekleyen Sipariş</h3>
                    <div class="num">${stats.pendingOrders}</div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>