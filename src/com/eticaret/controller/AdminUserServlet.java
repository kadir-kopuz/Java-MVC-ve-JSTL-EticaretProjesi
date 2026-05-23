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
import java.util.List;

@WebServlet("/admin/users")
public class AdminUserServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
User adminUser = (User) session.getAttribute("currentUser"); 

if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
    response.sendRedirect(request.getContextPath() + "/login"); 
    return;
}

        List<User> allUsers = userDAO.getAllUsers();
        request.setAttribute("users", allUsers);
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}