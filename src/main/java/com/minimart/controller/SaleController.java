package com.minimart.controller;

import com.minimart.model.Product;
import com.minimart.model.Sale;
import com.minimart.model.SaleItem;
import com.minimart.service.ProductService;
import com.minimart.service.SaleService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sales")
public class SaleController {
    
    @Autowired
    private SaleService saleService;
    
    @Autowired
    private ProductService productService;
    
    private static final String CART_SESSION_KEY = "shopping_cart";
    
    @SuppressWarnings("unchecked")
    private Map<Long, CartItem> getCart(HttpSession session) {
        Map<Long, CartItem> cart = (Map<Long, CartItem>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new HashMap<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }
    
    @GetMapping
    public String showSalesPage(Model model, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cart", cart.values());
        double total = cart.values().stream()
                .mapToDouble(item -> item.getSubtotal())
                .sum();
        model.addAttribute("total", total);
        return "sales/cart";
    }
    
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity, HttpSession session) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        
        Map<Long, CartItem> cart = getCart(session);
        
        if (product.getQuantity() < quantity) {
            return "redirect:/sales?error=insufficient_stock";
        }
        
        if (cart.containsKey(productId)) {
            CartItem item = cart.get(productId);
            int newQuantity = item.getQuantity() + quantity;
            if (product.getQuantity() < newQuantity) {
                return "redirect:/sales?error=insufficient_stock";
            }
            item.setQuantity(newQuantity);
            item.setSubtotal(newQuantity * product.getPrice());
        } else {
            CartItem item = new CartItem();
            item.setProductId(productId);
            item.setProductName(product.getName());
            item.setPrice(product.getPrice());
            item.setQuantity(quantity);
            item.setSubtotal(quantity * product.getPrice());
            cart.put(productId, item);
        }
        
        return "redirect:/sales";
    }
    
    @GetMapping("/remove-from-cart/{productId}")
    public String removeFromCart(@PathVariable Long productId, HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        cart.remove(productId);
        return "redirect:/sales";
    }
    
    @PostMapping("/checkout")
    public String checkout(HttpSession session) {
        Map<Long, CartItem> cart = getCart(session);
        
        if (cart.isEmpty()) {
            return "redirect:/sales?error=empty_cart";
        }
        
        // Verify stock availability before checkout
        for (CartItem cartItem : cart.values()) {
            Product product = productService.getProductById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + cartItem.getProductId()));
            
            if (product.getQuantity() < cartItem.getQuantity()) {
                return "redirect:/sales?error=insufficient_stock";
            }
        }
        
        Sale sale = new Sale();
        List<SaleItem> saleItems = new ArrayList<>();
        double totalAmount = 0;
        
        for (CartItem cartItem : cart.values()) {
            Product product = productService.getProductById(cartItem.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + cartItem.getProductId()));
            
            SaleItem saleItem = new SaleItem();
            saleItem.setSale(sale);
            saleItem.setProduct(product);
            saleItem.setQuantity(cartItem.getQuantity());
            saleItem.setPrice(cartItem.getPrice());
            saleItem.setSubtotal(cartItem.getSubtotal());
            saleItems.add(saleItem);
            
            totalAmount += cartItem.getSubtotal();
        }
        
        sale.setSaleItems(saleItems);
        sale.setTotalAmount(totalAmount);
        
        try {
            saleService.saveSale(sale);
            cart.clear();
            return "redirect:/sales?success=checkout_complete";
        } catch (IllegalArgumentException e) {
            return "redirect:/sales?error=insufficient_stock";
        }
    }
    
    // Inner class for cart items
    public static class CartItem {
        private Long productId;
        private String productName;
        private double price;
        private int quantity;
        private double subtotal;
        
        public Long getProductId() { return productId; }
        public void setProductId(Long productId) { this.productId = productId; }
        public String getProductName() { return productName; }
        public void setProductName(String productName) { this.productName = productName; }
        public double getPrice() { return price; }
        public void setPrice(double price) { this.price = price; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
    }
}
