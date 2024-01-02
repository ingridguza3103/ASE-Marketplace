package com.example.Marketplace.repository;

import com.example.Marketplace.model.Message;
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
        messageRepository.deleteAll();
    }

    @Test
    void findMessagesFromChat() {
        messageRepository.save(message);

        ArrayList<Message> chat = (ArrayList<Message>) messageRepository.findMessagesFromChat(senderId, recipientId);

        assertEquals(messageId,chat.get(0).getMessageId());


    }

    @Test
    void checkChatAlreadyExists() {
        messageRepository.save(message);

        assertTrue(messageRepository.checkChatAlreadyExists(senderId, recipientId));
    }

    @Test
    void checkChatNotAlreadyExists() {
        assertFalse(messageRepository.checkChatAlreadyExists(senderId, recipientId));
    }
}