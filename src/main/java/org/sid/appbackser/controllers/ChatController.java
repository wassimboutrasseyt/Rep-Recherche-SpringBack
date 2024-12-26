package org.sid.appbackser.controllers;

import org.sid.appbackser.entities.Message;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.enums.GroupType;
import org.sid.appbackser.services.MessageService;
import org.sid.appbackser.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    // @Autowired
    // private MessageService messageService;

    // @Autowired
    // private UserService userService;

    // // Send group messages to all users in the group
    // @MessageMapping("/chat.sendGroupMessage")
    // @SendTo("/topic/group")
    // public Message sendGroupMessage(Message message) {
    //     // Fetch the sender from MySQL
    //     User sender = userService.findUserByName(message.getSender().getNom());
    //     message.setSender(sender); // Set the sender

    //     // Set other necessary fields for group message
    //     message.setTimestamp(java.time.LocalDateTime.now());

    //     return messageService.saveMessage(message); // Save to MongoDB
    // }

    // // Send private messages to a specific user
    // @MessageMapping("/chat.sendPrivateMessage")
    // @SendToUser("/queue/private")
    // public Message sendPrivateMessage(Message message) {
    //     // Fetch the sender from MySQL
    //     User sender = userService.findUserByName(message.getSender().getNom());
    //     message.setSender(sender); // Set the sender

    //     // Fetch the receiver from MySQL
    //     User receiver = userService.findUserById(message.getReceiver().getId());
    //     message.setReceiver(receiver); // Set the receiver

    //     message.setTimestamp(java.time.LocalDateTime.now());
    //     message.setGroupType(GroupType.specific); // Mark as a private message

    //     return messageService.saveMessage(message); // Save to MongoDB
    // }
}
