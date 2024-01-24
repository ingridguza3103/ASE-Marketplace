package com.example.Marketplace.repository;

import com.example.Marketplace.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product("test", 10.50, 2000, 1, 1L, "", "");
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

    @Test
    void testAvailableInDesiredQty() {
        productRepository.save(product);
        int quantity = 3;

        assertTrue(productRepository.availableInDesiredQty(product.getId(), quantity));
    }

    @Test
    void testNotAvailableInDesiredQty() {
        product.setQuantity(2);
        productRepository.save(product);
        int quantity = 3;

        assertFalse(productRepository.availableInDesiredQty(product.getId(), quantity));
    }

    @Test
    void testFindProductBySellerId() {
        List<Product> userProducts = new ArrayList<>();
        userProducts.add(product);

        productRepository.save(product);

        List<Product> dbList = productRepository.findBySellerId(product.getSellerId());

        assertEquals(userProducts, dbList);

    }

    @Test
    void testFindProductBySellerIdNoProducts() {

        List<Product> userProducts = productRepository.findBySellerId(product.getSellerId());

        assertTrue(userProducts.isEmpty());

    }

}