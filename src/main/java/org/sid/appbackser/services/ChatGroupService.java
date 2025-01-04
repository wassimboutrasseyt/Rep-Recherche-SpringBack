package org.sid.appbackser.services;

import java.util.List;

import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.enums.ChatGroupType;

public interface ChatGroupService {
    ChatGroup getChatGroupById(String id);
    ChatGroup createChatGroup(Integer projectId, String name, ChatGroupType type, List<Integer> members);
    List<ChatGroup> getChatGroupsByProject(Integer projectId);

    List<ChatGroupDTO> getChatGroupsForAccount(Integer accountId);

    void addMember(String chatGroupId, Integer memberId);
    void removeMember(String chatGroupId, Integer memberId);
    List<Integer> getMembers(String chatGroupId);
    //in this method we will check if the user is a member of the chat group, the existance of the project and the chat group
    void checkIncomming(Integer projectId,String chatGroupId, Integer accountId);
}
