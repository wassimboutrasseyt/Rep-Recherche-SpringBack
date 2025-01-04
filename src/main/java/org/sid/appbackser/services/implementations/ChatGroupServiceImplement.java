package org.sid.appbackser.services.implementations;

import org.sid.appbackser.services.ChatGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.sid.appbackser.dto.ChatGroupDTO;
import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.repositories.ChatGroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;

@Service
public class ChatGroupServiceImplement implements ChatGroupService {

    @Autowired
    private ChatGroupRepository chatGroupRepository;

    @Autowired
    private ProjectRepository projectRepository;


    @Override
    public ChatGroup createChatGroup(Integer projectId, String groupName, ChatGroupType type, List<Integer> memberIds) {
        ChatGroup chatGroup = new ChatGroup();
        chatGroup.setProjectId(projectId);
        chatGroup.setName(groupName);
        chatGroup.setType(type);
        chatGroup.setMembers(memberIds);
        // Save chatGroup to the database (assuming you have a repository for ChatGroup)
        chatGroupRepository.save(chatGroup);
        return chatGroup;
    }

	@Override
	public void addMember(String groupId, Integer memberId) {
		
        ChatGroup chatGroup = chatGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
        List<Integer> members = chatGroup.getMembers();
        if (!members.contains(memberId)) {
            members.add(memberId);
            chatGroup.setMembers(members);
            chatGroupRepository.save(chatGroup);
        }
	}

	@Override
	public List<Integer> getMembers(String groupId) {
		
        ChatGroup chatGroup = chatGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
        return chatGroup.getMembers();
	}

	@Override
	public void removeMember(String groupId, Integer memberId) {
		// Implementation here
        ChatGroup chatGroup = chatGroupRepository.findById(groupId).orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
        List<Integer> members = chatGroup.getMembers();
        if (members.contains(memberId)) {
            members.remove(memberId);
            chatGroup.setMembers(members);
            chatGroupRepository.save(chatGroup);
        }
	}

	@Override
	public List<ChatGroup> getChatGroupsByProject(Integer projectId) {
		// Implementation here
        return chatGroupRepository.findByProjectId(projectId);
	}

    @Override
    public ChatGroup getChatGroupById(String id) {
        return chatGroupRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
    }

    @Override
    public void checkIncomming(Integer projectId, String chatGroupId, Integer accountId) {
        //check project existance
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new IllegalArgumentException("Project not found"));
        //check chatGroup existance
        ChatGroup chatGroup = chatGroupRepository.findById(chatGroupId).orElseThrow(() -> new IllegalArgumentException("Chat group not found"));
        //check chatGroup existance in the project
        if (chatGroup.getProjectId() != projectId) {
            throw new IllegalArgumentException("Chat group does not belong to the project" + chatGroupId + " project : " + projectId);
        }
        //check if the user is a member of the chat group
        if (!chatGroup.getMembers().contains(accountId)) {
            throw new IllegalArgumentException("User "+ accountId + " is not a member of the chat group :" + chatGroupId);
        }
    }

    @Override
    public List<ChatGroupDTO> getChatGroupsForAccount(Integer userId) {
        List<ChatGroup> chatGroups = chatGroupRepository.findByMembersContains(userId);
        return chatGroups.stream().map(ChatGroupDTO::new).collect(Collectors.toList());
    }
}
