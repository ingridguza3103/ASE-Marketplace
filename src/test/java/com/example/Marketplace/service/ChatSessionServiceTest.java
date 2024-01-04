package com.example.Marketplace.service;

import com.example.Marketplace.model.ChatSession;
import com.example.Marketplace.model.Message;
import com.example.Marketplace.repository.ChatSessionRepository;
import com.example.Marketplace.service.impl.ChatSessionServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ChatSessionServiceTest {

    @Mock
    ChatSessionRepository chatSessionRepository;
    @InjectMocks
    ChatSessionServiceImpl chatSessionService;

    private ChatSession chatSession;

    Long sessionId;
    Long buyerId;
    Long sellerId;
    ArrayList<Message> messages;
    Message message;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sessionId = 1L;
        buyerId = 3L;
        sellerId = 4L;

        chatSession = new ChatSession(buyerId, sellerId);

        message = new Message(buyerId, sellerId, "Hello there", LocalDateTime.now(), sessionId);

        messages = new ArrayList<>();
        messages.add(message);


    }

    @AfterEach
    void tearDown() {
        chatSession = null;
        message = null;
        messages = null;
        sessionId = null;
        buyerId = null;
        sellerId = null;
        Mockito.reset(chatSessionRepository);
    }

    @Test
    void testRestoreChatSession() {
        when(chatSessionRepository.findById(sessionId)).thenReturn(Optional.ofNullable(chatSession));

        ChatSession session = chatSessionService.restoreChatSession(sessionId);

        assertNotNull(session);
        assertEquals(chatSession, session);
    }

    @Test
    void testRestoreChatSessionByUserIds() {
        when(chatSessionRepository.findByUserIds(sellerId, buyerId)).thenReturn(chatSession);

        ChatSession session = chatSessionService.restoreChatSessionByUserIds(sellerId, buyerId);

        assertNotNull(session);
        assertEquals(chatSession, session);
    }

    @Test
    void testSessionAlreadyExists() {
        when(chatSessionRepository.existsById(sessionId)).thenReturn(true);

        assertTrue(chatSessionService.sessionAlreadyExists(sessionId));
    }

    @Test
    void testSessionNotAlreadyExists() {
        when(chatSessionRepository.existsById(sessionId)).thenReturn(false);

        assertFalse(chatSessionService.sessionAlreadyExists(sessionId));
    }

    @Test
    void testSessionAlreadyExistsByUserIds() {
        when(chatSessionRepository.existsByUserIds(sellerId, buyerId)).thenReturn(true);

        assertTrue(chatSessionService.sessionAlreadyExistsByUserIds(sellerId, buyerId));
    }

    @Test
    void testSessionNotAlreadyExistsByUserIds() {
        when(chatSessionRepository.existsByUserIds(sellerId, buyerId)).thenReturn(false);

        assertFalse(chatSessionService.sessionAlreadyExistsByUserIds(sellerId, buyerId));
    }
}