package org.sid.appbackser.controllers;

import org.sid.appbackser.entities.Message;
import org.sid.appbackser.services.MessageService;
import org.sid.appbackser.entities.GroupType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private MessageService messageService;

    // Send group messages to all users in the group
    @MessageMapping("/chat.sendGroupMessage")
    @SendTo("/topic/group") // Broadcast to all group members
    public Message sendGroupMessage(Message message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        message.setGroupType(GroupType.Default); // Default group type
        return messageService.saveMessage(message); // Save to MongoDB
    }

    // Send private messages to a specific user
    @MessageMapping("/chat.sendPrivateMessage")
    @SendToUser("/queue/private") // This sends the message to the user who sent the request
    public Message sendPrivateMessage(Message message) {
        message.setTimestamp(java.time.LocalDateTime.now());
        message.setGroupType(GroupType.specific); // Mark as a private message
        return messageService.saveMessage(message); // Save to MongoDB
    }
}
