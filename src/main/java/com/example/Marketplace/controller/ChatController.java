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
import org.springframework.web.bind.annotation.PostMapping;


/**
 * This controller class represents the REST endpoint for the chat functionality. It provides methods to initialize and
 * restore ChatSessions and send messages to the desired User.
 */
@Controller
public class ChatController {
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatSessionService chatSessionService;
    @Autowired
    private MessageService messageService;

    /**
     * This method creates a ChatSession or restores it if a ChatSession between two users already exists
     * @param buyerId buyerId represents the User that opens or initiates the chat
     * @param sellerId sellerId represents the User the current User wants to write to
     * @return status 200 and the ChatSession that was created or restored.
     */
    @PostMapping("/chat/{buyerId}/{sellerId}")
    public ResponseEntity<ChatSession> openChat(@PathVariable("buyerId") String buyerId, @PathVariable("sellerId") String sellerId) {
        ChatSession session;
        Long buyer = Long.parseLong(buyerId);
        Long seller = Long.parseLong(sellerId);
        if (!chatSessionService.sessionAlreadyExistsByUserIds(buyer, seller)) {
            session = new ChatSession(buyer, seller);
        } else {
            session = chatSessionService.restoreChatSessionByUserIds(buyer, seller);
        }
        return ResponseEntity.ok(session);

    }

    /**
     * This method handles messages that a User wants to send. The message is stored to the messages table, the ChatSession is fetched
     * and updated and the message is send to the recipient via the convertAndSendToUser method of SimpMessagingTemplate.
     * This method converts the Message to a WebSocketMessage and sends it to the recipients WebSocket session.
     * @param message
     */
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

    /**
     * This get mapping is used to restore an existing Chat
     * @param senderId the sender
     * @param recipientId the recipient
     * @return the ChatSession and status code 200
     */
    @GetMapping("/messages/{senderId}/{recipientId}")
    public ResponseEntity<ChatSession> getChat(@PathVariable("senderId") String senderId, @PathVariable("recipientId") String recipientId) {
        return ResponseEntity.ok(chatSessionService.restoreChatSessionByUserIds(Long.parseLong(senderId), Long.parseLong(recipientId)));
    }
}
