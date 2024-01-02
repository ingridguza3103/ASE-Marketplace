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

    @BeforeEach
    void setUp() {
        messageId = 1L;
        senderId = 2L;
        recipientId = 12L;
        content = "Hello this is a test message.";
        timestamp = LocalDateTime.now();

        message = new Message(messageId, senderId, recipientId, content, timestamp);
    }

    @AfterEach
    void tearDown() {
        message = null;
    }

    @Test
    void setMessageId() {
        Long newId = 32L;

        message.setMessageId(newId);

        assertEquals(newId, message.getMessageId());
    }

    @Test
    void getMessageId() {
        assertEquals(messageId, message.getMessageId());
    }

    @Test
    void getSenderId() {
        assertEquals(senderId, message.getSenderId());
    }

    @Test
    void setSenderId() {
        Long newSenderId = 4L;
        message.setSenderId(newSenderId);

        assertEquals(newSenderId, message.getSenderId());
    }

    @Test
    void getRecipientId() {
        assertEquals(recipientId, message.getRecipientId());
    }

    @Test
    void setRecipientId() {
        Long newRecipientId = 3L;
        message.setRecipientId(newRecipientId);

        assertEquals(newRecipientId, message.getRecipientId());
    }

    @Test
    void getContent() {
        assertEquals(content, message.getContent());
    }

    @Test
    void setContent() {
        String newContent = "Hello There.";
        message.setContent(newContent);

        assertEquals(newContent, message.getContent());
    }

    @Test
    void getTimestamp() {
        assertEquals(timestamp, message.getTimestamp());
    }

    @Test
    void setTimestamp() {
        LocalDateTime newTime = LocalDateTime.now();
        message.setTimestamp(newTime);

        assertEquals(newTime, message.getTimestamp());
    }
}