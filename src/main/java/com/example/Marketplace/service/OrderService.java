package com.example.Marketplace.service;

import com.example.Marketplace.model.Order;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    List<Order> getAllOrders();

    Order placeOrder(Order order);

    /**
     * cancel an order if it is not older than  few minutes
     * @param order the order to cancel
     */
    boolean cancelOrder(Order order);
}
