package com.example.Marketplace.repository;

import com.example.Marketplace.model.Message;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    private Message message;

    private Long messageId;

    private Long senderId;
    private Long recipientId;
    private String content;
    private LocalDateTime timestamp;

    private Long sessionId;


    @BeforeEach
    void setUp() {
        messageId = 1L;
        senderId = 2L;
        recipientId = 12L;
        content = "Hello this is a test message.";
        timestamp = LocalDateTime.now();
        sessionId = 23L;

        message = new Message(senderId, recipientId, content, timestamp, sessionId);

    }

    @AfterEach
    void tearDown() {
        message = null;
        messageRepository.deleteAll();
    }

    @Test
    @Transactional
    void testFindMessagesFromChat() {
        messageRepository.save(message);

        // Fetch the message and validate it
        ArrayList<Message> chat = (ArrayList<Message>) messageRepository.findMessagesFromChat(senderId, recipientId);

        assertTrue(chat.contains(message));


    }

    @Test
    void testCheckChatAlreadyExists() {
        messageRepository.save(message);

        assertTrue(messageRepository.checkChatAlreadyExists(senderId, recipientId));
    }

    @Test
    void tesCheckChatNotAlreadyExists() {
        assertFalse(messageRepository.checkChatAlreadyExists(senderId, recipientId));
    }

    @Test
    void testFindMessagesBySessionId() {
        messageRepository.save(message);

        ArrayList<Message> chat = (ArrayList<Message>) messageRepository.findMessagesBySessionId(sessionId);

        assertEquals(sessionId,chat.get(0).getSessionId());


    }
}