package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.enums.ChatGroupType;

public interface ChatGroupService {
    ChatGroup getChatGroupById(String id);
    ChatGroup createChatGroup(Integer projectId, String name, ChatGroupType type, List<Integer> members);
    List<ChatGroup> getChatGroupsByProject(Integer projectId);
    void addMember(String chatGroupId, Integer memberId);
    void removeMember(String chatGroupId, Integer memberId);
    List<Integer> getMembers(String chatGroupId);
}
