package com.example.Marketplace.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ChatSessionTest {

    private ChatSession chatSession;

    private Long id;
    private Long sellerId;
    private Long buyerId;
    private ArrayList<Message> chat;

    private Message message;

    @BeforeEach
    void setUp() {
        id = 1L;
        sellerId = 12L;
        buyerId = 4L;

        message = new Message(buyerId, sellerId, "Hello", LocalDateTime.now(), id);

        chat = new ArrayList<>();
        chat.add(message);

        chatSession = new ChatSession(buyerId, sellerId);
        chatSession.setId(id);
        chatSession.addMessageToChat(message);



    }

    @AfterEach
    void tearDown() {
        chatSession = null;
        message = null;
        chat = null;
        sellerId = null;
        buyerId = null;
        id = null;
    }

    @Test
    void testSetId() {
        Long newId = 4L;
        chatSession.setId(newId);

        assertEquals(newId, chatSession.getId());
    }

    @Test
    void testGetId() {
        assertEquals(id, chatSession.getId());
    }

    @Test
    void testGetBuyerId() {
        assertEquals(buyerId, chatSession.getBuyerId());
    }

    @Test
    void testSetBuyerId() {
        Long newBuyerId = 3L;
        chatSession.setBuyerId(newBuyerId);

        assertEquals(newBuyerId, chatSession.getBuyerId());
    }

    @Test
    void testGetSellerId() {
        assertEquals(sellerId, chatSession.getSellerId());
    }

    @Test
    void testSetSellerId() {
        Long newSellerId = 3L;
        chatSession.setSellerId(newSellerId);

        assertEquals(newSellerId, chatSession.getSellerId());
    }

    @Test
    void testGetChat() {
        assertEquals(chat, chatSession.getChat());
    }

    @Test
    void testSetChat() {
        ArrayList<Message> newChat = chat;
        chatSession.setChat(newChat);

        assertEquals(newChat, chatSession.getChat());
    }

    @Test
    void testAddMessageToChat() {
        Message newMessage = new Message(sellerId, buyerId, "Hello there", LocalDateTime.now(), id);
        chatSession.addMessageToChat(newMessage);

        assertEquals(2, chatSession.getChat().size());
        assertTrue(chatSession.getChat().contains(newMessage));
    }
}