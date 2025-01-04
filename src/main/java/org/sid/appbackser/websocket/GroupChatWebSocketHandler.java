package org.sid.appbackser.websocket;

import org.apache.commons.logging.LogFactory;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.MessageService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    private static final Logger logger = LoggerFactory.getLogger(GroupChatWebSocketHandler.class);
    
    private Account accountOfTheSession;

    private String projectId;
    private String groupId;

    @Override
    @Transactional
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        projectId = extractPathSegment(session, 3); // Project ID
        groupId = extractPathSegment(session, 4);   // Group ID

        accountOfTheSession = getAccountFromSession(session);
        String groupChatId = generateGroupChatId(projectId, groupId);
        sessions.computeIfAbsent(groupChatId, k -> new ArrayList<>()).add(session);
        
        //check incomming data , project id, chat group id and account id and membership
        chatGroupService.checkIncomming(Integer.valueOf(projectId), groupId, accountOfTheSession.getId());
        
        logger.info("Connected to group chat within project: {} with the following user: {}, {}", projectId, accountOfTheSession.getEmail(), accountOfTheSession.getId());
    }
    
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Message receivedMessage = objectMapper.readValue(message.getPayload(), Message.class);
    
        String groupChatId = generateGroupChatId(projectId, groupId);
    
        Message newMessage = messageService.createMessageToGroup(
                accountOfTheSession.getId(),
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
