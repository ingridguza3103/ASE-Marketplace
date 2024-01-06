package com.example.Marketplace.service;



import com.example.Marketplace.model.Message;

import java.util.List;

public interface MessageService {

    List<Message> getChat(Long senderId, Long recipientId);

    Message saveMessage(Message message);

    List<Message> restoreChatSession(Long sessionId);

}
