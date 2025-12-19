package com.minimart.controller;

import com.minimart.model.Product;
import com.minimart.model.Sale;
import com.minimart.model.SaleItem;
import com.minimart.service.ProductService;
import com.minimart.service.SaleService;
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
    
    // Session-based cart (in a real application, use session attributes or database)
    private Map<Long, CartItem> cart = new HashMap<>();
    
    @GetMapping
    public String showSalesPage(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("cart", cart.values());
        double total = cart.values().stream()
                .mapToDouble(item -> item.getSubtotal())
                .sum();
        model.addAttribute("total", total);
        return "sales/cart";
    }
    
    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long productId, @RequestParam int quantity) {
        Product product = productService.getProductById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid product Id:" + productId));
        
        if (product.getQuantity() < quantity) {
            return "redirect:/sales?error=insufficient_stock";
        }
        
        if (cart.containsKey(productId)) {
            CartItem item = cart.get(productId);
            item.setQuantity(item.getQuantity() + quantity);
            item.setSubtotal(item.getQuantity() * product.getPrice());
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
    public String removeFromCart(@PathVariable Long productId) {
        cart.remove(productId);
        return "redirect:/sales";
    }
    
    @PostMapping("/checkout")
    public String checkout() {
        if (cart.isEmpty()) {
            return "redirect:/sales?error=empty_cart";
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
        
        saleService.saveSale(sale);
        cart.clear();
        
        return "redirect:/sales?success=checkout_complete";
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
