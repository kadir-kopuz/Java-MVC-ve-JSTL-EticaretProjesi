package com.eticaret.controller;

import com.eticaret.dao.OrderDAO;
import com.eticaret.model.CartItem;
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

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderDAO orderDAO = new OrderDAO();

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute("currentUser");

        if (currentUser == null) {
            request.setAttribute("errorMessage", "Sipariş oluşturabilmek için lütfen önce giriş yapın.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        Double grandTotal = (Double) session.getAttribute("grandTotal");

        if (cart == null || cart.isEmpty()) {
            response.sendRedirect("cart");
            return;
        }

        Order order = new Order();
        order.setUserId(currentUser.getId());
        order.setTotalAmount(grandTotal != null ? grandTotal : 0.0);

        boolean success = orderDAO.createOrder(order, cart);

        if (success) {
            session.removeAttribute("cart");
            session.removeAttribute("grandTotal");
            session.setAttribute("successMessage", "Siparişiniz başarıyla oluşturuldu! Keyifli alışverişler dileriz.");
            response.sendRedirect(request.getContextPath() + "/my-orders");
        } else {
            request.setAttribute("errorMessage", "Siparişiniz oluşturulurken teknik bir hata meydana geldi.");
            request.getRequestDispatcher("cart.jsp").forward(request, response);
        }
    }
}