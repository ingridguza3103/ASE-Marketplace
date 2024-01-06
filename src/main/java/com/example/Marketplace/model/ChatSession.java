package com.example.Marketplace.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represent a Chat between two users it holds the list of all the messages within that chat.
 * The session is persistent so if there already exists a session between two users this session is fetched from
 * the db instead of creating a new session.
 */
@Entity
@Table(name="chat_sessions")
public class ChatSession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long buyerId;  // is sender and recipient in the chat session
    private Long sellerId; // is sender and recipient in the chat session
    @OneToMany(mappedBy = "sessionId")
    private List<Message> chat;


    public ChatSession() {}
    public ChatSession(Long buyerId, Long sellerId) {
        this.buyerId = buyerId;
        this.sellerId = sellerId;

        // set chat to an empty array list when creating a new ChatSession
        this.chat = new ArrayList<>();
    }


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getSellerId() {
        return sellerId;
    }

    public void setSellerId(Long sellerId) {
        this.sellerId = sellerId;
    }

    public List<Message> getChat() {
        return chat;
    }

    public void setChat(List<Message> chat) {
        this.chat = chat;
    }

    /**
     * Adds a message to the list of messages in the chat.
     * @param message the message to add
     */
    public void addMessageToChat(Message message) {
        chat.add(message);
    }
}
