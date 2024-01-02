package com.example.Marketplace.service.impl;

import com.example.Marketplace.model.Message;
import com.example.Marketplace.repository.MessageRepository;
import com.example.Marketplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used for calling the SQL queries from MessageRepository
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageRepository messageRepository;

    /**
     * This method is to restore the messages of a chat between two users
     * @param senderId the sender
     * @param recipientId the recipient
     * @return the list of messages
     */
    @Override
    public List<Message> getChat(Long senderId, Long recipientId) {
        return messageRepository.findMessagesFromChat(senderId, recipientId);
    }
}
