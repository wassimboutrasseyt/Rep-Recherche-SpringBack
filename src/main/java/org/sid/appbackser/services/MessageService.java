package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Message;
import org.sid.appbackser.enums.MessageType;

public interface MessageService {
    Message createMessageToGroup(Integer senderId, String chatGroupId, String content, MessageType type);
    List<Message> getMessagesForChatGroup(String chatGroup);
    List<Message> getMessagesForAccountOnGroup(Integer accountId, String chatGroupId);
    void deleteMessage(String messageId);
}
