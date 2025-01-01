package org.sid.appbackser.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class BaseWebSocketHandler extends TextWebSocketHandler {

    @Autowired
    protected AccountService accountService;

    protected final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    protected final Map<String, List<WebSocketSession>> sessions = new ConcurrentHashMap<>();

    protected Account getAccountFromSession(WebSocketSession session) {
        return accountService.getAccountFromToken(session.getPrincipal());
    }

    protected String extractPathSegment(WebSocketSession session, int index) {
        String path = session.getUri().getPath();
        String[] pathSegments = path.split("/");
        if (pathSegments.length <= index) {
            throw new IllegalArgumentException("Invalid URI path: " + path);
        }
        return pathSegments[index];
    }

    protected void broadcastMessage(String id, Object message, WebSocketSession senderSession) {
        List<WebSocketSession> groupSessions = sessions.get(id);
        if (groupSessions != null) {
            groupSessions.parallelStream().forEach(session -> {
                try {
                    if (session.isOpen() && !session.equals(senderSession)) {
                        session.sendMessage(new TextMessage(objectMapper.writeValueAsString(message)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
    }
    

    protected void removeSession(WebSocketSession session, String id) {
        List<WebSocketSession> groupSessions = sessions.get(id);
        if (groupSessions != null) {
            groupSessions.remove(session);
            if (groupSessions.isEmpty()) {
                sessions.remove(id); // Clean up if no sessions are left
            }
        }
    }
}
