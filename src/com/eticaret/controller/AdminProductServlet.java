package com.eticaret.controller;

import com.eticaret.dao.ProductDAO;
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

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect("../login");
            return;
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if ("delete".equals(action) && idParam != null) {
            try {
                int id = Integer.parseInt(idParam);
                productDAO.deleteProduct(id);
            } catch (NumberFormatException e) {
                System.out.println("Geçersiz ürün ID biçimi.");
            }
        }

        List<Product> allProducts = productDAO.getAllProducts();
        request.setAttribute("products", allProducts);
        request.getRequestDispatcher("products.jsp").forward(request, response);
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
        String categoryIdParam = request.getParameter("categoryId");
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String priceParam = request.getParameter("price");
        String stockParam = request.getParameter("stock");
        String imageUrl = request.getParameter("imageUrl");
        String isActiveParam = request.getParameter("isActive");

        if (name == null || name.trim().isEmpty()) {
            sendError(request, response, "Ürün adı boş bırakılamaz.");
            return;
        }
        if (categoryIdParam == null || categoryIdParam.equals("0")) {
            sendError(request, response, "Lütfen geçerli bir kategori seçin.");
            return;
        }

        double price = 0;
        int stock = 0;
        try {
            price = Double.parseDouble(priceParam);
            stock = Integer.parseInt(stockParam);
            
            if (price <= 0) {
                sendError(request, response, "Ürün fiyatı 0'dan büyük olmalıdır.");
                return;
            }
            if (stock < 0) {
                sendError(request, response, "Stok miktarı negatif olamaz.");
                return;
            }
        } catch (NumberFormatException e) {
            sendError(request, response, "Fiyat veya stok alanı geçersiz bir sayısal biçimde.");
            return;
        }

        Product p = new Product();
        p.setCategoryId(Integer.parseInt(categoryIdParam));
        p.setName(name);
        p.setDescription(description);
        p.setPrice(price);
        p.setStock(stock);
        p.setImageUrl(imageUrl);
        p.setActive("true".equals(isActiveParam));

        boolean success;
        if (idParam != null && !idParam.trim().isEmpty()) {
            p.setId(Integer.parseInt(idParam));
            success = productDAO.updateProduct(p);
        } else {
            success = productDAO.addProduct(p);
        }

        if (success) {
            response.sendRedirect("products");
        } else {
            sendError(request, response, "Veritabanına kaydedilirken teknik bir hata oluştu.");
        }
    }

    private void sendError(HttpServletRequest request, HttpServletResponse response, String msg) throws ServletException, IOException {
        request.setAttribute("errorMessage", msg);
        request.getRequestDispatcher("product-form").forward(request, response);
    }
}