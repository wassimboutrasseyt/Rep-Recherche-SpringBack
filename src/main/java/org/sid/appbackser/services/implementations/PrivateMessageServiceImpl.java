package org.sid.appbackser.services.implementations;

import org.sid.appbackser.entities.PrivateMessage;
import org.sid.appbackser.enums.MessageType;
import org.sid.appbackser.repositories.PrivateMessageRepository;
import org.sid.appbackser.services.PrivateMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PrivateMessageServiceImpl implements PrivateMessageService {

    @Autowired
    private PrivateMessageRepository privateMessageRepository;

    @Override
    public PrivateMessage createPrivateMessage(Integer senderId, Integer receiverId, Integer projectId, String content, MessageType type) {
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setSenderId(senderId);
        privateMessage.setReceiverId(receiverId);
        privateMessage.setProjectId(projectId);
        privateMessage.setContent(content);
        privateMessage.setType(type);
        return privateMessageRepository.save(privateMessage);
    }

    @Override
    public PrivateMessage getPrivateMessageById(String id) {
        Optional<PrivateMessage> privateMessage = privateMessageRepository.findById(id);
        return privateMessage.orElse(null);
    }

    @Override
    public List<PrivateMessage> getAllPrivateMessages() {
        return privateMessageRepository.findAll();
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesBySenderId(Integer senderId) {
        return privateMessageRepository.findBySenderId(senderId);
    }

    @Override
    public List<PrivateMessage> getPrivateMessagesByReceiverId(Integer receiverId) {
        return privateMessageRepository.findByReceiverId(receiverId);
    }

    @Override
    public void deletePrivateMessage(String id) {
        privateMessageRepository.deleteById(id);
    }
}