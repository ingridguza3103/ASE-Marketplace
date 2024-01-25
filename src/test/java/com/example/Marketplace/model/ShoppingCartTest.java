package com.example.Marketplace.model;

import com.example.Marketplace.repository.ProductRepository;

import com.example.Marketplace.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ShoppingCartTest {

    @Mock
    private ProductServiceImpl productService;
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ShoppingCart shoppingCart;

    private HashMap<Product, Integer> products;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        products = new HashMap<>();

    }

    @AfterEach
    void tearDown() {
        products.clear();
        Mockito.reset(productRepository, productService);
    }

    @Test
    void testAddItemWithEnoughQuantity() {
        Product product = new Product("Test", 10.3, 2000, 2, 3L, "", "");
        int quantity = 5;

        products.put(product, quantity);

        when(productService.enoughQuantity(product.getId(), quantity)).thenReturn(true);

        shoppingCart.addItem(product, quantity);

        assertEquals(products.get(product), quantity);
    }

    @Test
    void testAddItemWithInsufficientQuantity() {
        Product product = new Product("Test", 10.3, 2000, 2, 3L, "", "");
        int quantity = 10;

        products.put(product, quantity);
        when(productService.enoughQuantity(product.getId(), quantity)).thenReturn(false);

        shoppingCart.addItem(product, quantity);

        assertTrue(shoppingCart.getItems().isEmpty());
    }

    @Test
    void testRemoveItem() {
        Product add = new Product("test", 2.80, 2000, 1, 34L, "", "");
        int qty = 3;

        when(productService.enoughQuantity(add.getId(), qty)).thenReturn(true);
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