package com.example.Marketplace.repository;

import com.example.Marketplace.model.ChatSession;
import com.example.Marketplace.model.Message;
import com.example.Marketplace.service.impl.ChatSessionServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest
class ChatSessionRepositoryTest {

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    private ChatSession chatSession;

    Long sessionId;
    Long buyerId;
    Long sellerId;
    ArrayList<Message> messages;
    Message message;

    @BeforeEach
    void setUp() {
        sessionId = 1L;
        buyerId = 3L;
        sellerId = 4L;

        chatSession = new ChatSession(buyerId, sellerId);

        message = new Message(buyerId, sellerId, "Hello there", LocalDateTime.now(), sessionId);

        messages = new ArrayList<>();
        messages.add(message);
        chatSession.setChat(messages);
    }

    @AfterEach
    void tearDown() {
        chatSession = null;
        message = null;
        messages = null;
        sessionId = null;
        buyerId = null;
        sellerId = null;

        chatSessionRepository.deleteAll();
    }

    @Test
    void testFindByUserIds() {
        chatSessionRepository.save(chatSession);

        ChatSession session = chatSessionRepository.findByUserIds(buyerId, sellerId);

        assertEquals(chatSession, session);

    }

    @Test
    void testExistsByUserIds() {
        chatSessionRepository.save(chatSession);

        boolean exists = chatSessionRepository.existsByUserIds(buyerId, sellerId);

        assertTrue(exists);
    }

    @Test
    void testNotExistsByUserIds() {
        boolean exists = chatSessionRepository.existsByUserIds(buyerId, sellerId);

        assertFalse(exists);
    }
}