package com.eticaret.controller;

import com.eticaret.dao.CategoryDAO;
import com.eticaret.dao.ProductDAO;
import com.eticaret.model.Category;
import com.eticaret.model.Product;
import com.eticaret.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/product-form")
public class AdminProductFormServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
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

        List<Category> categories = categoryDAO.getAllActiveCategories();
        request.setAttribute("categories", categories);

        String idParam = request.getParameter("id");
        if (idParam != null && !idParam.trim().isEmpty()) {
            int id = Integer.parseInt(idParam);
            Product p = productDAO.getProductById(id);
            request.setAttribute("product", p);
        }

        request.getRequestDispatcher("product-form.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}