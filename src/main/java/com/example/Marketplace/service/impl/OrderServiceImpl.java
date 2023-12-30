package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.Order;
import com.example.Marketplace.repository.OrderRepository;
import com.example.Marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service

public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    @Override
    public Order placeOrder(Order order) {
        order.setOrderPlaced(LocalDateTime.now());
        return orderRepository.save(order);
    }

    /**
     * cancel an order if it is not older than  few minutes
     * @param order the order to cancel
     */
    @Override
    public boolean cancelOrder(Order order) {
         LocalDateTime currentTime = LocalDateTime.now();
         // Calculate the time passed between order creation time and current time
         Duration timePassed = Duration.between(order.getOrderPlaced(), currentTime);
         // if the order is younger than 10 minutes allow cancelling
         if (timePassed.toMinutes() <= 10) {
             orderRepository.delete(order);
             return true;
         }

         return false;
    }
}
