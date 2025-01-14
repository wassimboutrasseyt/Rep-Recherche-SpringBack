package org.sid.appbackser.services.implementations;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.sid.appbackser.dto.MessageDTO;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.entities.User;
import org.sid.appbackser.enums.MessageType;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.MessageRepository;
import org.sid.appbackser.services.AccountService;
import org.sid.appbackser.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImplement implements MessageService{

    @Autowired
    private MessageRepository messageRepository;
    
    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired 
    private AccountService accountService;

    @Override
    public List<MessageDTO> getMessagesForChatGroup(String chatGroupId) {
        List<Message> messages = messageRepository.findByChatGroupId(chatGroupId);
        List<MessageDTO> messageDTOs = new ArrayList<>();
        for (Message message : messages) {
            messageDTOs.add(convertToDTO(message));
        }
        return messageDTOs;
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
    public MessageDTO convertToDTO(Message message) {
        User senderUser = accountService.getAccount(message.getSenderId()).getUser();
        return new MessageDTO(
            message.getId(),
            message.getSenderId(),
            senderUser.getFirstName(),
            senderUser.getLastName(),
            message.getContent(),
            message.getTimestamp(),
            message.getType()
        );
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
