package org.sid.appbackser.dto;

import java.time.Instant;
import java.util.List;

import org.sid.appbackser.entities.ChatGroup;
import org.sid.appbackser.enums.ChatGroupType;

import lombok.Data;

@Data
public class ChatGroupDTO {
    private String id;
    private Integer projectId;
    private String name;
    private ChatGroupType type;
    private List<Integer> members;
    private Instant createdAt;
    
    // copy constructor
    public ChatGroupDTO(ChatGroup chatGroup) {
        this.id = chatGroup.getId();
        this.projectId = chatGroup.getProjectId();
        this.name = chatGroup.getName();
        this.type = chatGroup.getType();
        this.members = chatGroup.getMembers();
        this.createdAt = chatGroup.getCreatedAt();
    }
}
