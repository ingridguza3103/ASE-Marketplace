package com.example.Marketplace.controller;

import com.example.Marketplace.model.ChatSession;
import com.example.Marketplace.model.Message;
import com.example.Marketplace.model.Notification;
import com.example.Marketplace.service.ChatSessionService;
import com.example.Marketplace.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ChatController {
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatSessionService chatSessionService;
    @Autowired
    private MessageService messageService;

    // TODO: create a method to create a new ChatSession if it doesn't exist yet when the user wants to enter a chat


    @MessageMapping("/chat")
    public void handleMessage(@Payload Message message) {
        // Retrieve recipient ID from the message
        Long recipientId = message.getRecipientId();

        // Save the message to the messages table
        Message savedMessage = messageService.saveMessage(message);

        // Fetch the chat session and add the message to the recipient's chat
        ChatSession recipientSession = chatSessionService.restoreChatSession(savedMessage.getSessionId());
        recipientSession.addMessageToChat(savedMessage);

        // Send a notification only to the recipient's WebSocket session
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(recipientId), "queue/messages", new Notification(savedMessage.getMessageId(), savedMessage.getSenderId(), recipientId, savedMessage.getContent()));

    }

    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<ChatSession> getChat(@PathVariable("senderId") String senderId, @PathVariable("recipientId") String recipientId) {
        return ResponseEntity.ok(chatSessionService.restoreChatSessionByUserIds(Long.parseLong(senderId), Long.parseLong(recipientId)));
    }
}
