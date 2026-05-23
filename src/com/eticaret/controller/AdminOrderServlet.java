package com.eticaret.controller;

import com.eticaret.dao.OrderDAO;
import com.eticaret.model.Order;
import com.eticaret.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/orders")
public class AdminOrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Listeleme işlemi
        List<Order> allOrders = orderDAO.getAllOrders();
        request.setAttribute("orders", allOrders);
        request.getRequestDispatcher("orders.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String orderIdParam = request.getParameter("orderId");
        String status = request.getParameter("status");

        if (orderIdParam != null && status != null) {
            try {
                int orderId = Integer.parseInt(orderIdParam);
                orderDAO.updateOrderStatus(orderId, status);
            } catch (NumberFormatException e) {
                System.out.println("Sipariş güncellemede geçersiz ID.");
            }
        }

        response.sendRedirect("orders");
    }
}