package com.example.Marketplace.controller;

import com.example.Marketplace.service.OrderService;
import com.example.Marketplace.service.TokenService;
import com.example.Marketplace.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenService tokenService;
    // TODO: implement!!


    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getOrders() {
        List<Order> orders = orderService.getAllOrders();
        return ResponseEntity.ok().body(orders);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable String userId) {
        Long id = Long.parseLong(userId);

        List<Order> userOrders = orderService.getUserOrders(id);
        return ResponseEntity.ok().body(userOrders);
    }
}
