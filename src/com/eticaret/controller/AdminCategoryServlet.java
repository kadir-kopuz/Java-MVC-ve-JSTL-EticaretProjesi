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
import java.util.List;

@WebServlet("/admin/categories")
public class AdminCategoryServlet extends HttpServlet {
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("delete".equals(action) && idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                String resultMessage = categoryDAO.deleteOrDeactivateCategory(id);
                request.setAttribute("infoMessage", resultMessage);
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz kategori ID biçimi.");
            }
        }

        List<Category> allCategories = categoryDAO.getAllCategories();
        request.setAttribute("categories", allCategories);
        request.getRequestDispatcher("categories.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        request.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            request.setAttribute("errorMessage", "Kategori adı boş bırakılamaz!");
            request.getRequestDispatcher("category-form.jsp").forward(request, response);
            return;
        }

        Category cat = new Category();
        cat.setName(name);

        boolean success;
        if (idParam != null && !idParam.trim().isEmpty()) {
            cat.setId(Integer.parseInt(idParam));
            success = categoryDAO.updateCategory(cat);
        } else {
            success = categoryDAO.addCategory(cat);
        }

        if (success) {
            response.sendRedirect("categories");
        } else {
            request.setAttribute("errorMessage", "Kategori kaydedilirken teknik bir hata oluştu.");
            request.getRequestDispatcher("category-form.jsp").forward(request, response);
        }
    }
}