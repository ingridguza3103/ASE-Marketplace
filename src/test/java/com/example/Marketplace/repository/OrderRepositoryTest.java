package com.example.Marketplace.repository;

import com.example.Marketplace.model.Order;
import com.example.Marketplace.model.OrderItem;
import com.example.Marketplace.model.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    private Long userId;
    private List<OrderItem> products;

    private LocalDateTime timestamp;

    private Order order;

    private OrderItem item1;
    private OrderItem item2;


    @BeforeEach
    void setUp() {
        order = new Order();
        // save the empty order object so the OrderItems can be properly assigned
        orderRepository.save(order);
        userId = 2L;
        //order.setId(1L);
        order.setUserId(userId);
        products = new ArrayList<>();
        Product product1 =  new Product("test", 10.50, 2000, 1, 1L, "");
        Product product2 = new Product("test2", 8.00, 2000, 1, 2L, "");

        productRepository.save(product1);
        productRepository.save(product2);

        item1 = new OrderItem(order, product1, 2);
        item2 = new OrderItem(order, product2, 3);

        orderItemRepository.save(item1);
        orderItemRepository.save(item2);



        products.add(item1);
        products.add(item2);
        order.setProducts(products);

        timestamp = LocalDateTime.now();
        order.setOrderPlaced(timestamp);
    }

    @AfterEach
    void tearDown() {
        order = null;
        orderRepository.deleteAll();
    }

    @Test
    void testFindAllByUserId() {
        // save the order to the db

        orderRepository.save(order);


        List<Order> userOrders = orderRepository.findAllByUserId(2L);

        assertEquals(1, userOrders.size(), "List size comparison");
        assertEquals(order, userOrders.get(0), "Check contains order");
    }
}