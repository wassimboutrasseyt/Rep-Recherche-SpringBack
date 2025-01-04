package org.sid.appbackser.websocket;

import org.apache.commons.logging.LogFactory;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.MessageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import org.slf4j.Logger;

public class GroupChatWebSocketHandler extends BaseWebSocketHandler {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ChatGroupService chatGroupService;

    @Autowired 
    private AccountService accountService;

    private Account account;

    private static final Logger logger = LoggerFactory.getLogger(GroupChatWebSocketHandler.class);
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String projectId = extractPathSegment(session, 3); // Project ID
        String groupId = extractPathSegment(session, 4);   // Group ID

        logger.info("principal : " + session.getPrincipal());
        account = accountService.getAccountFromToken(session.getPrincipal());
        logger.info("account from session" + account);
        String groupChatId = generateGroupChatId(projectId, groupId);
        sessions.computeIfAbsent(groupChatId, k -> new ArrayList<>()).add(session);

        session.sendMessage(new TextMessage("Connected to group chat within project: " + projectId + "\n by the following user" + account));
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message receivedMessage = objectMapper.readValue(message.getPayload(), Message.class);
        String projectId = extractPathSegment(session, 3); // Project ID
        String groupId = extractPathSegment(session, 4);   // Group ID
    
        String groupChatId = generateGroupChatId(projectId, groupId);
    
        Message newMessage = messageService.createMessageToGroup(
                getAccountFromSession(session).getId(),
                groupId,
                receivedMessage.getContent(),
                receivedMessage.getType()
        );
    
        broadcastMessage(groupChatId, newMessage, session); // Pass sender's session
    }
    
    private String generateGroupChatId(String projectId, String groupId) {
        return projectId + "-" + groupId;
    }
    
}
