package com.eticaret.controller;

import com.eticaret.dao.ProductDAO;
import com.eticaret.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/product-detail")
public class ProductDetailServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String idParam = request.getParameter("id");
        
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                int productId = Integer.parseInt(idParam);
                Product product = productDAO.getProductById(productId);
                
                if (product != null) {
                    request.setAttribute("product", product);
                    request.getRequestDispatcher("product-detail.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz Ürün ID formatı.");
            }
        }
        
        response.sendRedirect("home");
    }
}