package com.eticaret.controller;

import com.eticaret.dao.AdminDAO;
import com.eticaret.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private AdminDAO adminDAO = new AdminDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            request.setAttribute("errorMessage", "Bu alana erişim yetkiniz bulunmamaktadır. Lütfen yönetici hesabıyla giriş yapın.");
            request.getRequestDispatcher("../login.jsp").forward(request, response);
            return;
        }

        Map<String, Integer> stats = adminDAO.getDashboardStats();
        request.setAttribute("stats", stats);

        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}