package org.sid.appbackser.services.implementations;

import org.sid.appbackser.entities.PrivateConversation;
import org.sid.appbackser.entities.PrivateMessage;
import org.sid.appbackser.repositories.PrivateConversationRepository;
import org.sid.appbackser.repositories.PrivateMessageRepository;
import org.sid.appbackser.services.PrivateConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class PrivateConversationServiceImpl implements PrivateConversationService {

    @Autowired
    private PrivateConversationRepository conversationRepository;

    @Autowired
    private PrivateMessageRepository messageRepository;

    @Override
    public PrivateConversation getOrCreateConversation(Integer senderId, Integer receiverId, Integer projectId) {
        return conversationRepository.findBySenderIdAndReceiverId(senderId, receiverId)
            .orElseGet(() -> {
                PrivateConversation newConversation = new PrivateConversation();
                newConversation.setSenderId(senderId);
                newConversation.setReceiverId(receiverId);
                newConversation.setProjectId(projectId);
                return conversationRepository.save(newConversation);
            });
    }

    @Override
    public List<PrivateMessage> getMessagesForConversation(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}