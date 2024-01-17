package com.example.Marketplace.repository;

import com.example.Marketplace.model.OrderItem;
import com.example.Marketplace.model.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
