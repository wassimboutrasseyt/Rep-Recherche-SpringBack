package org.sid.appbackser.services.implementations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.enums.MessageType;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.MessageRepository;
import org.sid.appbackser.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImplement implements MessageService{

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Override
	public List<Message> getMessagesForChatGroup(String chatGroupId) {
        List<Message> messages = messageRepository.findByChatGroupId(chatGroupId);
        return messages;
	}
    
	@Override
    public Message createMessageToGroup(Integer senderId, String chatGroupId, String content, MessageType type) {
        ChatGroup chatGroup = chatGroupRepository.findById(chatGroupId).orElseThrow(() -> new RuntimeException("Chat group not found"));
        Message message = new Message();
        message.setSenderId(senderId);
        message.setChatGroup(chatGroup);
        message.setContent(content);
        message.setType(type);
        return messageRepository.save(message);
    }

	@Override
	public void deleteMessage(String messageId) {
        messageRepository.deleteById(messageId);
	}

	@Override
	public List<Message> getMessagesForAccountOnGroup(Integer accountId, String chatGroupId) {
        ChatGroup chatGroup = chatGroupRepository.findById(chatGroupId).orElseThrow(() -> new RuntimeException("Chat group not found"));
        List<Message> messages = messageRepository.findByChatGroupId(chatGroupId);
        List<Message> accountMessages = new ArrayList<>();
        for (Message message : messages) {
            if (message.getSenderId().equals(accountId)) {
            accountMessages.add(message);
            }
        }
        return accountMessages;
	}


}
