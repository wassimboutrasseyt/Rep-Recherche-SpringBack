package org.sid.appbackser.dto;

import java.time.Instant;

import org.sid.appbackser.entities.Group;
import org.sid.appbackser.enums.ProjectType;
import org.sid.appbackser.enums.ProjectVisibility;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectDTO {
    private Integer id;
    private String longName;
    private String shortName;
    private ProjectType type;
    private String category;
    private ProjectVisibility visibility;
    private String licenseName; // Optional, only for "logiciel"
    private String description;
    private Instant createdAt;
    private String generalChatGroupId; // Reference to general ChatGroup
    private String adminChatGroupId;   // Reference to admin ChatGroup
    private Group projectGroup;     // Reference to the associated project group
    private Group adminGroup;   

}