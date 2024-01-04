package com.example.Marketplace.service;

import com.example.Marketplace.model.Message;
import com.example.Marketplace.repository.MessageRepository;
import com.example.Marketplace.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MessageServiceTest {

    @Mock
    MessageRepository messageRepository;

    @InjectMocks
    private MessageServiceImpl messageService;

    private Message mockMessage;
    Long messageId;
    Long senderId;
    Long recipientId;
    String content;
    LocalDateTime timestamp;
    Long sessionId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        messageId = 1L;
        senderId = 2L;
        recipientId = 12L;
        content = "Hello this is a test message.";
        timestamp = LocalDateTime.now();
        sessionId = 4L;
        mockMessage = new Message(senderId, recipientId, content, timestamp, sessionId);
    }

    @AfterEach
    void tearDown() {
        mockMessage = null;
        Mockito.reset(messageRepository);
    }

    @Test
    void testGetChat() {
        ArrayList<Message> chat = new ArrayList<>();
        chat.add(mockMessage);
        when(messageRepository.findMessagesFromChat(senderId, recipientId)).thenReturn(chat);
        List<Message> retrievedChat = messageService.getChat(senderId, recipientId);

        assertEquals(chat.size(), retrievedChat.size());
        assertTrue(retrievedChat.contains(mockMessage));
    }

    @Test
    void testGetChatNoChat() {

        when(messageRepository.findMessagesFromChat(senderId, recipientId)).thenReturn(new ArrayList<>());
        List<Message> retrievedChat = messageService.getChat(senderId, recipientId);

        assertTrue(retrievedChat.isEmpty());
    }
}