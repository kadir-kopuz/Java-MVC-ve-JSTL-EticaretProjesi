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
            response.sendRedirect(request.getContextPath() + "/login");
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Türkçe kategorilerin veritabanına bozulmadan gitmesi için şarttır
        request.setCharacterEncoding("UTF-8");
        
        HttpSession session = request.getSession();
        User adminUser = (User) session.getAttribute("adminUser");

        if (adminUser == null || !"ADMIN".equals(adminUser.getRole())) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Formdan gelen Kategori verilerini yakalıyoruz
        String idParam = request.getParameter("id");
        String name = request.getParameter("name");

        Category category = new Category();
        category.setName(name);

        if (idParam != null && !idParam.trim().isEmpty()) {
            // ID parametresi varsa bu bir GÜNCELLEME işlemidir
            category.setId(Integer.parseInt(idParam));
            categoryDAO.updateCategory(category);
        } else {
            // ID parametresi yoksa bu YENİ KATEGORİ EKLEME işlemidir
            categoryDAO.addCategory(category);
        }

        // İşlem tamamlandıktan sonra Kategori Listeleme ekranına yönlendiriyoruz
        response.sendRedirect(request.getContextPath() + "/admin/categories");
    }
}