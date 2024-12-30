package org.sid.appbackser.websocket;

import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GroupChatWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired
    private AccountService accountService;

    private final Map<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();
    private Account account;

    // Register ObjectMapper with JavaTimeModule for handling Java 8 date/time types
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String chatGroupId = getChatGroupId(session);
        sessions.computeIfAbsent(chatGroupId, k -> new ArrayList<>()).add(session);

        account = accountService.getAccountFromToken(session.getPrincipal());
        session.sendMessage(new TextMessage("Connected successfully: " +
                "Account ID: " + account.getId() +
                ", Email: " + account.getEmail() +
                ", Role: " + account.getRole()));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message receivedMessage = objectMapper.readValue(message.getPayload(), Message.class);

        String chatGroupId = getChatGroupId(session);

        if (chatGroupService.getChatGroupById(chatGroupId) == null) {
            session.sendMessage(new TextMessage("Chat group not found"));
            return;
        }

        Message newMessage = messageService.createMessageToGroup(
                account.getId(),
                chatGroupId,
                receivedMessage.getContent(),
                receivedMessage.getType()
        );

        broadcastMessage(newMessage);
    }

    private String getChatGroupId(WebSocketSession session) {
        String path = session.getUri().getPath();
        String[] pathSegments = path.split("/");
        if (pathSegments.length < 4) {
            throw new IllegalArgumentException("Invalid URI path: " + path);
        }
        return pathSegments[3];
    }

    private void broadcastMessage(Message message) throws Exception {
        List<WebSocketSession> groupSessions = sessions.get(message.getChatGroup().getId().toString());
        if (groupSessions != null) {
            for (WebSocketSession session : groupSessions) {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String chatGroupId = getChatGroupId(session);
        List<WebSocketSession> groupSessions = sessions.get(chatGroupId);
        if (groupSessions != null) {
            groupSessions.remove(session);
            if (groupSessions.isEmpty()) {
                sessions.remove(chatGroupId); // Clean up if no sessions are left for the group
            }
        }
    }
}
