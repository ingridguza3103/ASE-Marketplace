package com.example.Marketplace.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setProductName("test");
        product.setPrice(10.50);
        product.setQuantity(2000);
        product.setCategoryId(1);
        product.setSellerId(1L);
        product.setPictureUrl("");

    }

    @AfterEach
    void tearDown() {
        product = null;
    }

    @Test
    void setId() {
        Long newId = 2L;
        product.setId(newId);
        assertEquals(newId, product.getId());
    }

    @Test
    void getId() {
        assertEquals(1, product.getId());
    }

    @Test
    void getProductName() {
        String name = "test";
        assertEquals(name, product.getProductName());
    }

    @Test
    void setProductName() {
        String newName = "testName";

        product.setProductName(newName);
        assertEquals(newName, product.getProductName());
    }

    @Test
    void getPrice() {
        double price = 10.50;
        assertEquals(price, product.getPrice());
    }

    @Test
    void setPrice() {
        double newPrice = 11.50;
        product.setPrice(newPrice);
        assertEquals(newPrice, product.getPrice());
    }

    @Test
    void getQuantity() {
        int availableQty = 2000;
        assertEquals(availableQty, product.getQuantity());
    }

    @Test
    void setQuantity() {
        int newAvailableQty = 1500;
        product.setQuantity(newAvailableQty);
        assertEquals(newAvailableQty, product.getQuantity());
    }

    @Test
    void isInStockTrue() {
        assertTrue(product.isInStock());
    }

    @Test
    void isInStockFalse() {
        product.setQuantity(0);
        assertFalse(product.isInStock());
    }

    @Test
    void getCategoryId() {
        Long categoryId = 1L;
        assertEquals(categoryId, product.getCategoryId());
    }

    @Test
    void setCategoryId() {
        int newCategoryId = 2;
        product.setCategoryId(newCategoryId);
        assertEquals(newCategoryId, product.getCategoryId());
    }

    @Test
    void getSellerId() {
        Long sellerID = 1L;
        assertEquals(sellerID, product.getSellerId());

    }

    @Test
    void setSellerId() {
        Long newSellerID = 2L;
        product.setSellerId(newSellerID);
        assertEquals(newSellerID, product.getSellerId());
    }

    @Test
    void getPictureUrl() {
        String pictureUrl = "";
        assertEquals(pictureUrl, product.getPictureUrl());
    }

    @Test
    void setPictureUrl() {
        String newPictureUrl = "test/picture/url";
        product.setPictureUrl(newPictureUrl);
        assertEquals(newPictureUrl, product.getPictureUrl());
    }
}