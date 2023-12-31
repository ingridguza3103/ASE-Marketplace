package com.example.Marketplace.model;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    Product product;

    Order order;
    OrderItem orderItem;

    int quantity;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "test", 10.50, 2000, 1, 21L, "");
        order = new Order();
        order.setOrderPlaced(LocalDateTime.now());

        orderItem = new OrderItem(order, product, 3);

        ArrayList<OrderItem> products = new ArrayList<>();
        products.add(orderItem);
        order.setProducts(products);
        order.setId(1L);
    }

    @AfterEach
    void tearDown() {
        product = null;
        order = null;
    }

    @Test
    void testGetTotal() {

        assertEquals(product.getPrice()*3, orderItem.getTotal());

    }

    @Test
    void testGetProduct() {

        assertEquals(product, orderItem.getProduct());
    }

    @Test
    void testGetOrder() {
        assertEquals(order, orderItem.getOrder());
    }
}