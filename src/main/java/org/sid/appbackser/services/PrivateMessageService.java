package org.sid.appbackser.services;
import org.sid.appbackser.entities.PrivateMessage;
import org.sid.appbackser.enums.MessageType;

import java.util.List;

public interface PrivateMessageService {
    PrivateMessage createPrivateMessage(Integer senderId, Integer receiverId, Integer projectId, String content, MessageType type);
    PrivateMessage getPrivateMessageById(String id);
    List<PrivateMessage> getAllPrivateMessages();
    List<PrivateMessage> getPrivateMessagesBySenderId(Integer senderId);
    List<PrivateMessage> getPrivateMessagesByReceiverId(Integer receiverId);
    void deletePrivateMessage(String id);
}