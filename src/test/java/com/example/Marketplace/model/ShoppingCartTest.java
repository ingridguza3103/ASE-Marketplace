package com.example.Marketplace.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.parameters.P;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class ShoppingCartTest {

    private ShoppingCart shoppingCart;

    private HashMap<Product, Integer> products;

    @BeforeEach
    void setUp() {
        shoppingCart = new ShoppingCart();
        products = new HashMap<>();
    }

    @AfterEach
    void tearDown() {
        shoppingCart = null;
    }

    @Test
    void testAddItem() {
        Product add = new Product(1L, "test", 2.80, 2000, 1, 34L, "");
        int qty = 3;

        products.put(add, qty);

        shoppingCart.addItem(add, qty);


        assertEquals(products, shoppingCart.getItems());
    }

    @Test
    void testRemoveItem() {
        Product add = new Product(1L, "test", 2.80, 2000, 1, 34L, "");
        int qty = 3;
        shoppingCart.addItem(add, qty);

        shoppingCart.removeItem(add, 2);

        // assert that new quantity equals 1
        assertEquals(1, shoppingCart.getItems().get(add));
    }



    /* TODO: fully implement this method first
    @Test
    void testOnCheckOut() {
    } */
}