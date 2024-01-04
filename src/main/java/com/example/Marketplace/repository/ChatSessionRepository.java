package com.example.Marketplace.repository;

import com.example.Marketplace.model.ChatSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChatSessionRepository extends JpaRepository<ChatSession, Long> {
    @Query("SELECT c FROM ChatSession c WHERE (c.buyerId = :buyerId AND c.sellerId = :sellerId) OR (c.buyerId = :sellerId AND c.sellerId = :buyerId)")
    ChatSession findByUserIds(Long buyerId, Long sellerId);
    @Query("SELECT EXISTS(SELECT c FROM ChatSession c WHERE (c.buyerId = :buyerId AND c.sellerId = :sellerId) OR (c.buyerId = :sellerId AND c.sellerId = :buyerId))")
    boolean existsByUserIds(Long buyerId, Long sellerId);
}
