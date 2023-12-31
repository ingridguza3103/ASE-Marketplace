package com.example.Marketplace.repository;

import com.example.Marketplace.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product(1L,"test", 10.50, 2000, 1, 1L, "");
    }

    @AfterEach
    void tearDown() {
        product = null;
        productRepository.deleteAll();
    }

    @Test
    void testCheckProductExists() {
        productRepository.save(product);
        assertTrue(productRepository.checkProductExists(product.getId()));
    }

    @Test
    void testCheckProductNotExists() {
        assertFalse(productRepository.checkProductExists(product.getId()));
    }
}