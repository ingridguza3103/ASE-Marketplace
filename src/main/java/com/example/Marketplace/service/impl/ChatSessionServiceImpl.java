package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.ChatSession;
import com.example.Marketplace.repository.ChatSessionRepository;
import com.example.Marketplace.service.ChatSessionService;
import exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This class is used for calling the SQL queries from ChatSessionRepository
 */
@Service
public class ChatSessionServiceImpl implements ChatSessionService {
    @Autowired
    ChatSessionRepository chatSessionRepository;

    @Override
    public ChatSession restoreChatSession(Long id) {
        return chatSessionRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));
    }

    @Override
    public ChatSession restoreChatSessionByUserIds(Long buyerId, Long sellerId) {
        return chatSessionRepository.findByUserIds(buyerId, sellerId);
    }

    @Override
    public boolean sessionAlreadyExists(Long id) {
        return chatSessionRepository.existsById(id);
    }

    @Override
    public boolean sessionAlreadyExistsByUserIds(Long buyerId, Long sellerId) {
        return chatSessionRepository.existsByUserIds(buyerId, sellerId);
    }
}
