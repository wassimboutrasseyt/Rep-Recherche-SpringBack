package org.sid.appbackser.websocket;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.PrivateConversation;
import org.sid.appbackser.entities.PrivateMessage;
import org.sid.appbackser.services.PrivateConversationService;
import org.sid.appbackser.services.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;

public class PrivateChatWebSocketHandler extends BaseWebSocketHandler {

    @Autowired
    private PrivateMessageService privateMessageService;

    @Autowired
    private PrivateConversationService privateConversationService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        Account account = getAccountFromSession(session);
        String receiverId = extractPathSegment(session, 3);
        String projectId = extractPathSegment(session, 4);

        String chatId = generateChatId(account.getId().toString(), receiverId, projectId);
        sessions.computeIfAbsent(chatId, k -> new ArrayList<>()).add(session);

        session.sendMessage(new TextMessage("Connected to private chat"));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Account account = getAccountFromSession(session);
        PrivateMessage receivedMessage = objectMapper.readValue(message.getPayload(), PrivateMessage.class);
    
        String receiverId = receivedMessage.getReceiverId().toString();
        String projectId = extractPathSegment(session, 4);
        String chatId = generateChatId(account.getId().toString(), receiverId, projectId);
    
        PrivateConversation conversation = privateConversationService.getOrCreateConversation(
                account.getId(),
                Integer.parseInt(receiverId),
                Integer.parseInt(projectId)
        );
    
        PrivateMessage newMessage = privateMessageService.createPrivateMessage(
                account.getId(),
                Integer.parseInt(receiverId),
                receivedMessage.getContent(),
                receivedMessage.getType(),
                conversation
        );
    
        broadcastMessage(chatId, newMessage, session); // Pass sender's session
    }    

    private String generateChatId(String senderId, String receiverId, String projectId) {
        return projectId + "-" + (senderId.compareTo(receiverId) < 0 ? senderId + "-" + receiverId : receiverId + "-" + senderId);
    }
}
