package org.sid.appbackser.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.sid.appbackser.entities.Group;
import org.sid.appbackser.entities.Project;
import org.sid.appbackser.enums.ChatGroupType;
import org.sid.appbackser.repositories.GroupRepository;
import org.sid.appbackser.repositories.ProjectRepository;
import org.sid.appbackser.services.ChatGroupService;
import org.sid.appbackser.services.ProjectService;

@Service
public class ProjectServiceImplement implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    
    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ChatGroupService chatGroupService;

    public Project createProject(Project project) {

        Group projectGroup = new Group();
        projectGroup.setName(project.getShortName());
        projectGroup = groupRepository.save(projectGroup);

        Group adminGroup = new Group();
        adminGroup.setName(project.getShortName() + "-adm");
        adminGroup = groupRepository.save(adminGroup);

        project.setProjectGroup(projectGroup);
        project.setAdminGroup(adminGroup);

        project = projectRepository.save(project);

        // Creating Generale chat group for the project
        List<Integer> accountIds = project.getAdminGroup().getAccounts().stream()
            .map(account -> account.getId())
            .collect(Collectors.toList());
        
        chatGroupService.createChatGroup(project.getId(), project.getShortName() + "-adm", ChatGroupType.ADMIN, accountIds);


        return project;

    }
}
