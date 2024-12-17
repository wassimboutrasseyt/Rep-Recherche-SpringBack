package org.sid.appbackser.services;

import org.sid.appbackser.entities.Message;
import org.sid.appbackser.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    // @Autowired
    // private MessageRepository messageRepository;

    // public Message saveMessage(Message message) {
    //     return messageRepository.save(message);
    // }

    // public List<Message> getMessagesForGroup(Long groupId) {
    //     return messageRepository.findByGroupId(groupId);
    // }

    // public List<Message> getMessagesForUser(Long userId) {
    //     return messageRepository.findByReceiverId(userId); // For private messages
    // }
}
