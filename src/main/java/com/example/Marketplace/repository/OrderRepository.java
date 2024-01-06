package com.example.Marketplace.repository;

import com.example.Marketplace.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
/**
 * Repository class for SQL queries in the orders table
 */
public interface OrderRepository extends JpaRepository<Order, Long> {
}
