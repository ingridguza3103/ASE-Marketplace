package com.example.Marketplace.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

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
        sessionId = 5L;

        message = new Message(senderId, recipientId, content, timestamp, sessionId);
        message.setMessageId(messageId);
    }

    @AfterEach
    void tearDown() {
        message = null;
    }

    @Test
    void testSetMessageId() {
        Long newId = 32L;

        message.setMessageId(newId);

        assertEquals(newId, message.getMessageId());
    }

    @Test
    void testGetMessageId() {
        assertEquals(messageId, message.getMessageId());
    }

    @Test
    void testGetSenderId() {
        assertEquals(senderId, message.getSenderId());
    }

    @Test
    void testSetSenderId() {
        Long newSenderId = 4L;
        message.setSenderId(newSenderId);

        assertEquals(newSenderId, message.getSenderId());
    }

    @Test
    void testGetRecipientId() {
        assertEquals(recipientId, message.getRecipientId());
    }

    @Test
    void testSetRecipientId() {
        Long newRecipientId = 3L;
        message.setRecipientId(newRecipientId);

        assertEquals(newRecipientId, message.getRecipientId());
    }

    @Test
    void testGetContent() {
        assertEquals(content, message.getContent());
    }

    @Test
    void testSetContent() {
        String newContent = "Hello There.";
        message.setContent(newContent);

        assertEquals(newContent, message.getContent());
    }

    @Test
    void testGetTimestamp() {
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    void testSetTimestamp() {
        LocalDateTime newTime = LocalDateTime.now();
        message.setTimestamp(newTime);

        assertEquals(newTime, message.getTimestamp());
    }

    @Test
    void testGetSessionId() {
        assertEquals(sessionId, message.getSessionId());
    }

    @Test
    void testSetSessionId() {
        Long newSessionId = 53L;
        message.setSessionId(newSessionId);

        assertEquals(newSessionId, message.getSessionId());
    }
}