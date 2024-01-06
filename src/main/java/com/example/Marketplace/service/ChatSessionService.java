package com.example.Marketplace.service;

import com.example.Marketplace.model.ChatSession;

public interface ChatSessionService {
    ChatSession restoreChatSession(Long id);
    ChatSession restoreChatSessionByUserIds(Long buyerId, Long sellerId);

    boolean sessionAlreadyExists(Long id);
    boolean sessionAlreadyExistsByUserIds(Long buyerId, Long sellerId);

}
