package com.eticaret.controller;

import com.eticaret.dao.CategoryDAO;
import com.eticaret.model.Category;
import com.eticaret.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/category-form")
public class AdminCategoryFormServlet extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            int id = Integer.parseInt(idParam);
            Category cat = categoryDAO.getCategoryById(id);
            request.setAttribute("category", cat);
        }

        request.getRequestDispatcher("category-form.jsp").forward(request, response);
    }
}