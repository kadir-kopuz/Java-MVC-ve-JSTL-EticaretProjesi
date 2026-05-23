package com.eticaret.controller;

import com.eticaret.dao.CategoryDAO;
import com.eticaret.dao.ProductDAO;
import com.eticaret.model.Category;
import com.eticaret.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
 
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();
    private CategoryDAO categoryDAO = new CategoryDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        List<Category> categoryList = categoryDAO.getAllActiveCategories();
        request.setAttribute("categories", categoryList);

        String categoryIdParam = request.getParameter("category");
        List<Product> productList;

        if (categoryIdParam != null && !categoryIdParam.trim().isEmpty()) {
            try {
                int categoryId = Integer.parseInt(categoryIdParam);
                productList = productDAO.getProductsByCategory(categoryId);
                request.setAttribute("selectedCategory", categoryId);
            } catch (NumberFormatException e) {
                productList = productDAO.getAllActiveProducts();
            }
        } else {
            productList = productDAO.getAllActiveProducts();
        }

        request.setAttribute("products", productList);

        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}