package com.example.Marketplace.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private List<OrderItem> products;

    private LocalDateTime timestamp;

    private Order order;

    private OrderItem item1;
    private OrderItem item2;


    @BeforeEach
    void setUp() {
        order = new Order();
        order.setId(1L);
        products = new ArrayList<>();
        item1 = new OrderItem(order, new Product(1L,"test", 10.50, 2000, 1, 1L, ""), 2);
        item2 = new OrderItem(order, new Product(2L,"test2", 8.00, 2000, 1, 2L, ""), 3);
        products.add(item1);
        products.add(item2);
        order.setProducts(products);

        timestamp = LocalDateTime.now();
        order.setOrderPlaced(timestamp);


    }

    @AfterEach
    void tearDown() {
        order = null;
    }

    @Test
    void calculateOrderTotal() {

        double orderTotal = item1.getTotal() + item2.getTotal();




        assertEquals(orderTotal, order.calculateOrderTotal());



    }

    @Test
    void setId() {
        long id = 2;
        order.setId(id);

        assertEquals(id, order.getId());
    }

    @Test
    void getId() {
        assertEquals(1, order.getId());
    }

    @Test
    void getProducts() {
        // since we added two OrderItems to products the getProducts size should be two
        List<OrderItem> retrievedItems = order.getProducts();

        assertEquals(2, retrievedItems.size(), "size check");
        assertTrue(retrievedItems.contains(item1) && retrievedItems.contains(item2), "contained items check");
    }

    @Test
    void setProducts() {
        List<OrderItem> productsSet = new ArrayList<>();
        productsSet.add(new OrderItem());

        order.setProducts(productsSet);
        assertEquals(productsSet, order.getProducts());
    }

    @Test
    void addProducts() {
        // old size of products is 2 in this test one item is added therefore we assert the new size to 3
        OrderItem addItem = new OrderItem(order, new Product(3L, "add", 3.00, 1000, 3, 34L, ""), 1);

        order.addProducts(addItem);

        assertEquals(3, order.getProducts().size(), "check new size");
        assertTrue(order.getProducts().contains(addItem), "check if contains new item");
    }

    @Test
    void getOrderPlaced() {

        assertEquals(timestamp, order.getOrderPlaced());

    }

    @Test
    void setOrderPlaced() {
        LocalDateTime time = LocalDateTime.now();

        order.setOrderPlaced(time);

        assertEquals(time, order.getOrderPlaced());
    }
}