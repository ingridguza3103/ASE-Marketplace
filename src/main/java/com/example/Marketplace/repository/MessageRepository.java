package com.example.Marketplace.repository;

import com.example.Marketplace.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository class for SQL queries in the messages table
 */
public interface MessageRepository extends JpaRepository<Message, Long> {

    /**
     * This method queries the messages table to find all messages from a chat between to users
     * @param senderId the sender
     * @param recipientId the recipient
     * @return the list of messages between those two
     */
    @Query("SELECT m FROM Message m WHERE (m.senderId = :senderId AND m.recipientId = :recipientId) OR (m.senderId = :recipientId AND m.recipientId = :senderId)")
    List<Message> findMessagesFromChat(Long senderId, Long recipientId);

    /**
     * This method is used to check if a chat between two users already exists
     * meaning that they already exchanged messages
     * @param senderId user 1
     * @param recipientId user 2
     * @return true if already exists, false otherwise
     */
    @Query("SELECT EXISTS(SELECT m FROM Message m WHERE  (m.senderId = :senderId AND m.recipientId = :recipientId) OR (m.senderId = :recipientId AND m.recipientId = :senderId))")
    boolean checkChatAlreadyExists(Long senderId, Long recipientId);

    @Query("SELECT m FROM Message m WHERE m.sessionId = :sessionId")
    List<Message> findMessagesBySessionId(Long sessionId);
}
