package com.eticaret.controller;

import com.eticaret.dao.UserDAO;
import com.eticaret.model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty()) {
            request.setAttribute("errorMessage", "E-posta ve şifre alanları boş bırakılamaz.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.loginUser(email, password);

        if (user != null) {
            HttpSession session = request.getSession();
            
            if ("ADMIN".equals(user.getRole())) {
                session.setAttribute("adminUser", user);
                response.sendRedirect("admin/dashboard"); 
            } else {
                session.setAttribute("currentUser", user);
                response.sendRedirect("home"); 
            }
        } else {
            request.setAttribute("errorMessage", "Hatalı e-posta veya şifre!");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}