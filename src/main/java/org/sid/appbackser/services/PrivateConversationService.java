package org.sid.appbackser.services;

import org.sid.appbackser.entities.PrivateConversation;
import org.sid.appbackser.entities.PrivateMessage;

import java.util.List;

public interface PrivateConversationService {

    PrivateConversation getOrCreateConversation(Integer senderId, Integer receiverId, Integer projectId);
    List<PrivateMessage> getMessagesForConversation(String conversationId);
}
