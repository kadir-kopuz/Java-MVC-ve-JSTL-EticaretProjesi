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

@WebServlet("/my-orders")
public class MyOrdersServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        List<Order> userOrders = orderDAO.getOrdersByUserId(currentUser.getId());
        request.setAttribute("orders", userOrders);

        request.getRequestDispatcher("my-orders.jsp").forward(request, response);
    }
}