package com.eticaret.controller;

import com.eticaret.dao.ProductDAO;
import com.eticaret.model.CartItem;
import com.eticaret.model.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductDAO productDAO = new ProductDAO();

    @Override
    @SuppressWarnings("unchecked")
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute("cart", cart);
        }

        String action = request.getParameter("action");
        String idParam = request.getParameter("id");

        if (action != null && idParam != null) {
            try {
                int productId = Integer.parseInt(idParam);

                if ("add".equals(action)) {
                    addToCart(request, cart, productId);
                } else if ("remove".equals(action)) {
                    removeFromCart(cart, productId);
                }
            } catch (NumberFormatException e) {
                System.out.println("Sepet işleminde geçersiz ID formatı.");
            }
        }

        double grandTotal = calculateGrandTotal(cart);
        session.setAttribute("grandTotal", grandTotal);

        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        HttpSession session = request.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        
        if (cart != null) {
            String productIdParam = request.getParameter("productId");
            String quantityParam = request.getParameter("quantity");

            try {
                int productId = Integer.parseInt(productIdParam);
                int quantity = Integer.parseInt(quantityParam);

                if (quantity <= 0) {
                    request.setAttribute("errorMessage", "Ürün adedi en az 1 olmalıdır.");
                } else {
                    for (CartItem item : cart) {
                        if (item.getProduct().getId() == productId) {
                            if (quantity > item.getProduct().getStock()) {
                                request.setAttribute("errorMessage", "Yetersiz stok! En fazla " + item.getProduct().getStock() + " adet ekleyebilirsiniz.");
                            } else {
                                item.updateQuantity(quantity);
                            }
                            break;
                        }
                    }
                }
            } catch (NumberFormatException e) {
                request.setAttribute("errorMessage", "Geçersiz adet formatı.");
            }
        }

        session.setAttribute("grandTotal", calculateGrandTotal(cart));
        request.getRequestDispatcher("cart.jsp").forward(request, response);
    }

    private void addToCart(HttpServletRequest request, List<CartItem> cart, int productId) {
        for (CartItem item : cart) {
            if (item.getProduct().getId() == productId) {
                int newQty = item.getQuantity() + 1;
                if (newQty <= item.getProduct().getStock()) {
                    item.updateQuantity(newQty);
                } else {
                    request.setAttribute("errorMessage", "Daha fazla ürün eklenemez, stok sınırına ulaşıldı.");
                }
                return;
            }
        }

        Product product = productDAO.getProductById(productId);
        if (product != null && product.getStock() > 0) {
            cart.add(new CartItem(product, 1));
        }
    }

    private void removeFromCart(List<CartItem> cart, int productId) {
        cart.removeIf(item -> item.getProduct().getId() == productId);
    }

    private double calculateGrandTotal(List<CartItem> cart) {
        double total = 0;
        if (cart != null) {
            for (CartItem item : cart) {
                total += item.getSubtotal();
            }
        }
        return total;
    }
}