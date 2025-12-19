package com.minimart.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void testProductCreation() {
        Product product = new Product();
        product.setId(1L);
        product.setName("Test Product");
        product.setPrice(10.0);
        product.setQuantity(50);
        product.setCategory("Test Category");

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(50, product.getQuantity());
        assertEquals("Test Category", product.getCategory());
    }

    @Test
    void testProductAllArgsConstructor() {
        Product product = new Product(1L, "Test Product", 10.0, 50, "Test Category");

        assertEquals(1L, product.getId());
        assertEquals("Test Product", product.getName());
        assertEquals(10.0, product.getPrice());
        assertEquals(50, product.getQuantity());
        assertEquals("Test Category", product.getCategory());
    }
}
