package org.sid.appbackser.controllers;

import java.security.Principal;

import org.sid.appbackser.dto.MessageDTO;
import org.sid.appbackser.dto.sentMessageDTO;
import org.sid.appbackser.entities.Account;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.enums.MessageType;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ChatGroupController {

    private static final Logger logger = LoggerFactory.getLogger(ChatGroupController.class);

    @Autowired
    private MessageService messageService;

    @Autowired
    private AccountService accountService;

    @MessageMapping("/chat/{projectId}/{groupId}")
    @SendTo("/topic/chat/{projectId}/{groupId}")
    public MessageDTO sendMessageToGroup(@DestinationVariable String projectId,
                                       @DestinationVariable String groupId,
                                       @Payload sentMessageDTO messageDTO
                                       ) {
        // Log method entry and parameters
        logger.info("Received message for group chat");
        logger.debug("projectId: {}, groupId: {}, message: {}", projectId, groupId, messageDTO);

        try {
            Account account = accountService.getAccount(messageDTO.getSenderId());

            // Log account details
            logger.debug("Account retrieved: {}", account);

            // Create a new message to save to the database (if needed)
            logger.info("Creating new message for group");
            Message newMessage = messageService.createMessageToGroup(
                    account.getId(),
                    groupId,
                    messageDTO.getContent(),
                    MessageType.TEXT
            );

            // Log the created message
            logger.debug("Message created: {}", newMessage);

            // Return the message to broadcast it to all subscribed clients
            return messageService.convertToDTO(newMessage);
        } catch (Exception e) {
            // Log any exceptions that occur
            logger.error("Error while processing group chat message", e);
            throw e; // Re-throw the exception to handle it upstream if needed
        }
    }
}
