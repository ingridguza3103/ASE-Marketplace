package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.Order;
import com.example.Marketplace.repository.OrderRepository;
import com.example.Marketplace.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

/**
 * This class is used for calling the SQL queries from OrderRepository
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    /**
     * fetch all orders from the db
     * @return the list of all orders
     */
    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return orderRepository.findAllByUserId(userId);
    }

    /**
     * This method is used to instantiate an order (saves the empty order in the db) in order to properly assign OrderItems
     * @param order the order to save
     * @return the Order
     */
    @Override
    public Order initiateOrder(Order order) {
        return orderRepository.save(order);
    }

    /**
     * This method is used to place an order (saves it in the db)
     * @param order the placed order
     * @return the Order
     */
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
